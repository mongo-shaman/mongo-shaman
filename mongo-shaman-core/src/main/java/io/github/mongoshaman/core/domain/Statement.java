package io.github.mongoshaman.core.domain;

import java.util.Objects;

public class Statement {

  private int lineNumber;
  private String code;

  public Statement() {
    /* Default constructor. */
  }

  public Statement(int lineNumber, String code) {
    this.lineNumber = lineNumber;
    this.code = code;
  }

  public int getLineNumber() {
    return this.lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Statement statement = (Statement) o;
    return lineNumber == statement.lineNumber && code.equals(statement.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lineNumber, code);
  }

  public String toString() {
    return "Statement(lineNumber=" + this.getLineNumber() + ", code=" + this.getCode() + ")";
  }
}
