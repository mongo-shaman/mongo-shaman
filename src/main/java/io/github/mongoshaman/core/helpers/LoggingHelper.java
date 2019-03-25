package io.github.mongoshaman.core.helpers;

import org.slf4j.Logger;

public final class LoggingHelper {

  private LoggingHelper() {
  }

  public static void debug(Logger log, String desc, Object set) {
    if (log.isDebugEnabled()) {
      log.debug("{}: {}", desc, set);
    }
  }

  public static void trace(Logger log, String desc, Object set) {
    if (log.isTraceEnabled()) {
      log.trace("{}: {}", desc, set);
    }
  }

  public static void trace(Logger log, String desc) {
    if (log.isTraceEnabled()) {
      log.trace("{}", desc);
    }
  }
}
