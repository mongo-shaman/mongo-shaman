package io.github.mongoshaman.core.commands;

import com.mongodb.Function;
import com.mongodb.client.MongoClient;
import io.github.mongoshaman.core.configuration.ShamanConfiguration;
import io.github.mongoshaman.core.domain.Execution;
import io.github.mongoshaman.core.domain.MigrationFile;
import io.github.mongoshaman.core.domain.statuses.ExecutionStatus;
import io.github.mongoshaman.core.domain.statuses.MigrationStatus;
import io.github.mongoshaman.core.exceptions.ShamanExecutionException;
import io.github.mongoshaman.core.exceptions.ShamanMigrationException;
import io.github.mongoshaman.core.executors.StatementExecutor;
import io.github.mongoshaman.core.providers.MigrationFilesProvider;
import io.github.mongoshaman.core.providers.MigrationFilesToStatementsConverter;
import io.github.mongoshaman.core.repositories.ShamanRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.mongoshaman.core.helpers.LoggingHelper.debug;
import static io.github.mongoshaman.core.helpers.LoggingHelper.trace;

public class MigrateCommand implements Command {

  private static final Logger log = LoggerFactory.getLogger(MigrateCommand.class);

  private static final String MSG_MIGRATION_ABORTED =
    "Migration process has been aborted. There are not any file to be migrated";


  private MigrationFilesToStatementsConverter statementsConverter;

  private MigrationFilesProvider migrationFilesProvider;

  private StatementExecutor statementExecutor;

  private ShamanRepository repository;

  private Set<MigrationFile> systemFiles;

  private Set<MigrationFile> migratedFiles;

  public MigrateCommand(final MongoClient mongoClient, final ShamanConfiguration configuration) {
    this.statementsConverter = new MigrationFilesToStatementsConverter();
    this.migrationFilesProvider = new MigrationFilesProvider(configuration.getLocation());
    this.repository = new ShamanRepository(mongoClient, configuration);
    this.statementExecutor = new StatementExecutor(repository);
  }

  @Override
  public void run() {
    systemFiles = migrationFilesProvider.get();

    if(systemFiles.isEmpty()) {
      log.warn(MSG_MIGRATION_ABORTED);
      return;
    }
    debug(log, "System files", systemFiles);

    migratedFiles = repository.findAll();
    debug(log, "Migrated files", migratedFiles);

    checkOrphans(SetUtils.difference(migratedFiles, systemFiles));

    final List<MigrationFile> filesToBeMigrated = getMigrationFilesToBeExecuted();

    filesToBeMigrated.forEach(this::initToBeMigratedFile);
    filesToBeMigrated.forEach(this::migrate);
  }

  private void migrate(MigrationFile tbe) {
    initMigration(tbe);
    MigrationStatus status = MigrationStatus.EXECUTED;

    log.info("Executing {}", tbe.getName());
    statementExecutor.execute(tbe);
    trace(log, "Executed %s", tbe.getName());

    final List<Exception> exceptions = tbe.getExecutions().stream().map(Execution::getException)
        .filter(Objects::nonNull).collect(Collectors.toList());

    if (CollectionUtils.isNotEmpty(exceptions)) {
      status = MigrationStatus.ERROR;
    }

    tbe.setStatus(status);
    repository.updateStatus(tbe, status);

    if (MigrationStatus.ERROR.equals(status)) {
      throw new ShamanExecutionException(exceptions);
    }
  }

  private void initMigration(MigrationFile tbe) {
    MigrationStatus status = MigrationStatus.EXECUTING;
    tbe.setStatus(status);
    repository.updateStatus(tbe, status);

  }

  private void initToBeMigratedFile(MigrationFile migrationFile) {
    migrationFile.setStatus(MigrationStatus.NOT_EXECUTED);
    migrationFile.setExecutions(statementsConverter.convert(migrationFile).stream().map(statement -> {
      final Execution execution = new Execution();
      execution.setFile(migrationFile);
      execution.setStatement(statement);
      execution.setStatus(ExecutionStatus.PENDING);
      return execution;
    }).collect(Collectors.toList()));
    repository.save(migrationFile);
  }

  private List<MigrationFile> getMigrationFilesToBeExecuted() {
    final List<MigrationFile> toBeExecuted = new ArrayList<>();

    systemFiles.stream().sorted(Comparator.comparing(MigrationFile::getOrder)).forEach(systemFile -> {
      final MigrationFile match = migratedFiles.stream()
          .filter(migratedFile -> migratedFile.getName().equals(systemFile.getName())).findFirst().orElse(systemFile);

      if (Objects.isNull(match.getStatus())) {
        match.setStatus(MigrationStatus.NOT_EXECUTED);
      }
      trace(log, "Matched file", match);

      if (match.getStatus().ordinal() > MigrationStatus.NOT_EXECUTED.ordinal()) {
        validateChecksum(systemFile, match);
        validateOrder(systemFile, match);
      }

      if (MigrationStatus.EXECUTED.equals(match.getStatus())) {
        log.info("Skipping {}, it is already executed.", match.getName());
        return;
      }

      toBeExecuted.add(match);
    });
    return toBeExecuted;
  }

  private void validateOrder(final MigrationFile systemFile, final MigrationFile match) {
    trace(log, "Validating Order", new Object[]{ systemFile.getOrder(), match.getOrder() } );
    if (match.getOrder().intValue() != systemFile.getOrder().intValue()
        && MigrationStatus.EXECUTED.equals(match.getStatus())) {
      final Function<Set<MigrationFile>, String> collectSortedNames =
          s -> s.stream().sorted(Comparator.comparing(MigrationFile::getOrder)).map(MigrationFile::getName)
              .collect(Collectors.joining(","));

      throw new ShamanMigrationException(ShamanMigrationException.Type.ORDER,
          collectSortedNames.apply(systemFiles), collectSortedNames.apply(migratedFiles));
    }
  }

  private void validateChecksum(final MigrationFile systemFile, final MigrationFile match) {
    trace(log, "Validating Checksum", new Object[]{ systemFile.getChecksum(), match.getChecksum() } );
    if (!StringUtils.equals(match.getChecksum(), systemFile.getChecksum())
        && MigrationStatus.EXECUTED.equals(match.getStatus())) {
      throw new ShamanMigrationException(ShamanMigrationException.Type.CHECKSUM, match.getName());
    }
  }

  private void checkOrphans(Set<MigrationFile> migratedOrphanFiles) {
    if (CollectionUtils.isNotEmpty(migratedOrphanFiles)) {
      trace(log, "Migrated orphans", migratedOrphanFiles);
      throw new ShamanMigrationException(ShamanMigrationException.Type.ORPHAN,
          migratedOrphanFiles.stream().map(MigrationFile::getName).collect(Collectors.joining(",")));
    }
  }
}
