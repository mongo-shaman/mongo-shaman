package io.github.mongoshaman.core.exceptions;

import io.github.mongoshaman.core.configuration.ShamanProperties;

public class ShamanPropertiesException extends ShamanException {

  public ShamanPropertiesException(ShamanProperties property) {
    super(getFormattedMessage(property));
  }

  public ShamanPropertiesException(ShamanProperties property, Throwable cause) {
    super(getFormattedMessage(property), cause);
  }

  public ShamanPropertiesException(String message) {
    super(message);
  }

  private static String getFormattedMessage(ShamanProperties property) {
    return String.format("Wrong %s property", property.getKey());
  }
}
