package io.github.mongoshaman.core.exceptions;

public abstract class ShamanException extends RuntimeException {
  public ShamanException(String message) {
    super(message);
  }

  public ShamanException(String message, Throwable cause) {
    super(message, cause);
  }
}
