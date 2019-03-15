package io.github.mongoshaman.core.domain;

import java.util.List;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;

import io.github.mongoshaman.core.domain.statuses.MigrationStatus;

public class MigrationFile {

  private Integer order;
  private String name;
  private String content;
  private String checksum;

  private MigrationStatus status;

  private List<Execution> executions;

  public MigrationFile(final Integer order, final String name, final String content) {
    this.order = order;
    this.name = name;
    this.setContent(content);
  }

  public MigrationFile() {}

  @Override
  public String toString() {
    return "MigrationFile({\"order\" : " + order + ", \"name\" : \"" + name + "\", \"checksum\" : \"" + checksum
        + "\", \"status\" : \"" + status + "\"})";
  }

  public Integer getOrder() {
    return this.order;
  }

  public String getName() {
    return this.name;
  }

  public String getContent() {
    return this.content;
  }

  public String getChecksum() {
    return this.checksum;
  }

  public MigrationStatus getStatus() {
    return this.status;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContent(String content) {
    this.content = content;
    this.setChecksum(calcChecksum(content));
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public void setStatus(MigrationStatus status) {
    this.status = status;
  }

  public List<Execution> getExecutions() {
    return executions;
  }

  public void setExecutions(List<Execution> executions) {
    this.executions = executions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MigrationFile that = (MigrationFile) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  private String calcChecksum(final String content) {
    return DigestUtils.md5Hex(content);
  }
}
