package io.github.mongoshaman.core.domain;

import io.github.mongoshaman.core.domain.statuses.MigrationStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MigrationFileTest {
  @Test
  public void test(){
    // Arrange
    final MigrationFile migrationFile1 = new MigrationFile();
    migrationFile1.setOrder(0);
    migrationFile1.setStatus(MigrationStatus.NOT_EXECUTED);
    migrationFile1.setContent("content1");
    migrationFile1.setName("name1");
    migrationFile1.setExecutions(new ArrayList<>());

    final MigrationFile migrationFile2 = new MigrationFile(1, "name2", "content2");

    final MigrationFile migrationFile3 = new MigrationFile(0, "name1", "content1");

    final MigrationFile migrationFile4 = new MigrationFile(0, "name4", "content1");

    // Act
    final Set<MigrationFile> set = new HashSet<>();
    set.add(migrationFile1);
    set.add(migrationFile2);
    set.add(migrationFile3);
    set.add(migrationFile4);

    // Assert
    assertEquals("Unexpected set size", 3, set.size());
    assertEquals("Unexpected equals result", migrationFile1, migrationFile1);
    assertEquals("Unexpected equals result", migrationFile1, migrationFile3);
    assertNotEquals("Unexpected equals result", migrationFile1, new Object());
    assertNotEquals("Unexpected equals result", migrationFile1, migrationFile2);
    assertNotEquals("Unexpected equals result", migrationFile1, migrationFile4);
  }
}
