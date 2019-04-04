package io.github.mongoshaman.core.exceptions;

import io.github.mongoshaman.core.configuration.ShamanDefaultProperties;

public class ShamanPropertiesException extends ShamanException {

  public ShamanPropertiesException(ShamanDefaultProperties property) {
    super(getFormattedMessage(property));
  }

  public ShamanPropertiesException(ShamanDefaultProperties property, Throwable cause) {
    super(getFormattedMessage(property), cause);
  }

  public ShamanPropertiesException(String message) {
    super(message);
  }

  private static String getFormattedMessage(ShamanDefaultProperties property) {
    return String.format("Wrong %s property", property.getKey());
  }
}
