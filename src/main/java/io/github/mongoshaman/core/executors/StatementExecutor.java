package io.github.mongoshaman.core.executors;

import io.github.mongoshaman.core.domain.Execution;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mongoshaman.core.domain.MigrationFile;
import io.github.mongoshaman.core.domain.statuses.ExecutionStatus;
import io.github.mongoshaman.core.helpers.LoggingHelper;
import io.github.mongoshaman.core.repositories.ShamanRepository;

import java.util.Objects;

public class StatementExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatementExecutor.class);

  private ShamanRepository repository;

  public StatementExecutor(final ShamanRepository repository) {
    this.repository = repository;
  }

  public void execute(final MigrationFile file) {
    for (Execution execution : file.getExecutions()) {
      execution.setException(execute(execution));
      repository.updateExecutionStatus(execution);
      if (Objects.nonNull(execution.getException())) {
        break;
      }
    }
  }

  private Exception execute(Execution execution) {
    LoggingHelper.trace(LOGGER, "Executing...", execution.getStatement().getCode());
    try {
      this.repository.getMongoDatabase().runCommand(new Document("eval", execution.getStatement().getCode()));
      execution.setStatus(ExecutionStatus.EXECUTED);
    } catch (Exception e) {
      execution.setStatus(ExecutionStatus.ERROR);
      return e;
    }
    return null;
  }

}
