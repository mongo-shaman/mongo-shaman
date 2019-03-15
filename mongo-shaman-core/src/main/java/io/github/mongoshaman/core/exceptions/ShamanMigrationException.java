package io.github.mongoshaman.core.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShamanMigrationException extends ShamanException {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShamanMigrationException.class);

  public enum Type {
    ORPHAN("There's an incoherence between migration files and database: %s"),
    CHECKSUM("The %s file content is different that already executed."),
    ORDER("The file order does not match with last execution.\nFiles (%s)\nLast Execution (%s)");

    private String message;

    Type(String message) {
      this.message = message;
    }

    private static String getFormattedMessage(Type type, String... args) {
      return String.format(type.message, (Object[]) args);
    }
  }

  public ShamanMigrationException(final Type type, final String... args) {
    super(Type.getFormattedMessage(type, args));
    LOGGER.error(this.getMessage());
  }
}
