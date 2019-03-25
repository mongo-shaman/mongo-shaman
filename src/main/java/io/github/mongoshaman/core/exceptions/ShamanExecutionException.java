package io.github.mongoshaman.core.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShamanExecutionException extends ShamanException {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShamanExecutionException.class);

  public ShamanExecutionException(final List<Exception> exceptions) {
    super(exceptions.stream().map(Exception::getLocalizedMessage).collect(Collectors.joining("\n")));
    LOGGER.error(this.getMessage());
  }


}
