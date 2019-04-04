package io.github.mongoshaman.core.providers;

import static io.github.mongoshaman.core.helpers.LoggingHelper.trace;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mongoshaman.core.configuration.ShamanDefaultProperties;
import io.github.mongoshaman.core.domain.MigrationFile;
import io.github.mongoshaman.core.exceptions.ShamanPropertiesException;

public class MigrationFilesProvider {

  private static final Logger log = LoggerFactory.getLogger(MigrationFilesProvider.class);

  private static final String MSG_NO_MIGRATION_FILES = "No migration files";
  private static final String EXTENSION_JS = ".js";

  private String location;

  public MigrationFilesProvider(final String location) {
    this.location = location;
  }

  public Set<MigrationFile> get() {
    try (final Stream<Path> paths = Files.walk(Paths.get(location))) {
      final AtomicInteger atomicInteger = new AtomicInteger(0);
      final Set<MigrationFile> files = paths.filter(Files::isRegularFile).map(InternalMigrationFile::new)
          .filter(file -> file.getFile().canRead()).filter(file -> file.getFile().getName().endsWith(EXTENSION_JS))
          .sorted(Comparator.comparing(file -> file.getFile().getName()))
          .map(file -> new MigrationFile(atomicInteger.getAndAdd(1), file.getFile().getName(), file.getContent()))
          .collect(Collectors.toSet());

      if (CollectionUtils.isEmpty(files)) {
        trace(log, MSG_NO_MIGRATION_FILES);
        return Collections.emptySet();
      }
      trace(log, "Files found: ", files.stream().map(MigrationFile::getName).collect(Collectors.joining(", ")));
      return files;
    } catch (IOException e) {
      throw new ShamanPropertiesException(ShamanDefaultProperties.LOCATION, e);
    }
  }

  private class InternalMigrationFile {
    private File file;
    private String content;

    private InternalMigrationFile(final Path path) {
      this.file = path.toFile();
      this.content = readAllLines(path);
    }

    private File getFile() {
      return this.file;
    }

    private String getContent() {
      return this.content;
    }

    private String readAllLines(Path path) {
      try {
        return String.join("\n", Files.readAllLines(path, Charset.defaultCharset()));
      } catch (IOException e) {
        log.error("Error reading file", path.toUri().toString());
        return null;
      }
    }
  }
}
