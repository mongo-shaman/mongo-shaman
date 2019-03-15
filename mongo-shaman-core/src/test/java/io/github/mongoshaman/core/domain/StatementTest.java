package io.github.mongoshaman.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.github.mongoshaman.core.domain.statuses.MigrationStatus;

public class StatementTest {
  @Test
  public void test(){
    // Arrange
    final Statement statement1 = new Statement(1, "1");
    final Statement statement2 = new Statement(2, "2");
    final Statement statement3 = new Statement(1, "1");
    final Statement statement4 = new Statement();
    statement4.setLineNumber(4);
    statement4.setCode("4");

    // Act
    final Set<Statement> set = new HashSet<>();
    set.add(statement1);
    set.add(statement2);
    set.add(statement3);
    set.add(statement4);

    // Assert
    assertEquals("Unexpected set size", 3, set.size());
    assertEquals("Unexpected equals result", statement1, statement1);
    assertEquals("Unexpected equals result", statement1, statement3);
    assertNotEquals("Unexpected equals result", statement1, new Object());
    assertNotEquals("Unexpected equals result", statement1, statement2);
    assertNotEquals("Unexpected equals result", statement1, statement4);
  }
}
