package io.github.mongoshaman.core.domain;

import io.github.mongoshaman.core.domain.statuses.ExecutionStatus;

public class Execution {

  private MigrationFile file;
  private Statement statement;
  private Exception exception;
  private ExecutionStatus status;

  public MigrationFile getFile() {
    return file;
  }

  public void setFile(MigrationFile file) {
    this.file = file;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public ExecutionStatus getStatus() {
    return status;
  }

  public void setStatus(ExecutionStatus status) {
    this.status = status;
  }
}
