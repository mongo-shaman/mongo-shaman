package io.github.mongoshaman.core.domain.meta;

import java.util.List;

import io.github.mongoshaman.core.domain.Execution;

public final class MigrationFileMeta {

  public static final String ORDER = "order";
  public static final String NAME = "name";
  public static final String CONTENT = "content";
  public static final String CHECKSUM = "checksum";

  public static final String STATUS = "status";

  private List<Execution> executions;

}
