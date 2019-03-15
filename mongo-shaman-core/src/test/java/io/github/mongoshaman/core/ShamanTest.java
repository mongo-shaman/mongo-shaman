package io.github.mongoshaman.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.github.mongoshaman.core.configuration.ShamanConfiguration;
import io.github.mongoshaman.core.configuration.ShamanProperties;
import io.github.mongoshaman.core.exceptions.ShamanExecutionException;
import io.github.mongoshaman.core.exceptions.ShamanMigrationException;
import io.github.mongoshaman.core.exceptions.ShamanPropertiesException;
import io.github.mongoshaman.testing.core.AbstractTest;

@RunWith(JUnit4.class)
public class ShamanTest extends AbstractTest {

  @Before
  public void init() {
    setDatabaseNameProperty();
  }

  @Test
  public void migrate() {
    // Arrange
    System.setProperty(ShamanProperties.LOCATION.getKey(), "db/migration/");
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);

    // Act
    shaman.migrate();

    // Assert
    assertInternalDocuments(3);
  }

  @Test
  public void clean() {
    // Arrange

    // Act
    ShamanFactory.getInstance(mongoClient).clean();

    // Assert
    assertInternalDocuments(0);
  }

  @Test(expected = ShamanPropertiesException.class)
  public void migrate_wrongLocation() {
    // Arrange
    System.setProperty(ShamanProperties.LOCATION.getKey(), "fake-path");
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);
    shaman.clean();

    // Act
    shaman.migrate();

    // Assert
    assertInternalDocuments(0);
  }

  @Test
  public void migrate_emptyLocation() {
    // Arrange
    System.setProperty(ShamanProperties.LOCATION.getKey(), "db/empty/");
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);
    shaman.clean();

    // Act
    shaman.migrate();

    // Assert
    assertInternalDocuments(0);
  }

  @Test
  public void migrate_alreadyExecuted() {
    // Arrange
    System.clearProperty(ShamanProperties.LOCATION.getKey());
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);
    shaman.clean();
    shaman.migrate();

    // Act
    shaman.migrate();

    // Assert
    assertInternalDocuments(3);
  }

  @Test(expected = ShamanPropertiesException.class)
  public void shamanInstantiation_null_databaseName() {
    // Arrange
    System.clearProperty(ShamanProperties.DATABASE_NAME.getKey());
    ShamanConfiguration.refresh();

    // Act
    ShamanFactory.getInstance(mongoClient).clean();

  }

  @Test(expected = ShamanExecutionException.class)
  public void migrate_wrongStatement() {
    // Arrange
    System.setProperty(ShamanProperties.LOCATION.getKey(), "db/migration-wrong-statement/");
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);
    shaman.clean();

    // Act
    shaman.migrate();
  }

  @Test
  public void migrate_wrongOrder() {
    // Arrange
    launchSuccessCleanAndMigrate();

    // Act
    ShamanMigrationException exception = testMigrationExceptions("db/migration-wrong-order/");

    // Assert
    assertTrue("Unexpected message",
        exception.getMessage().contains("The file order does not match with last execution"));
  }

  @Test
  public void migrate_wrongChecksum() {
    // Arrange
    launchSuccessCleanAndMigrate();

    // Act
    ShamanMigrationException exception = testMigrationExceptions("db/migration-wrong-checksum/");

    // Arrange
    assertTrue("Unexpected message", exception.getMessage().contains("file content is different"));
  }

  @Test
  public void migrate_orphans() {
    // Arrange
    launchSuccessCleanAndMigrate();

    // Act
    ShamanMigrationException exception = testMigrationExceptions("db/migration-orphans/");

    // Arrange
    assertTrue("Unexpected message",
        exception.getMessage().contains("There's an incoherence between migration files and database"));
  }

  private void launchSuccessCleanAndMigrate() {
    System.clearProperty(ShamanProperties.LOCATION.getKey());
    ShamanConfiguration.refresh();
    Shaman shaman = ShamanFactory.getInstance(mongoClient);
    shaman.clean();
    shaman.migrate();
  }

  private ShamanMigrationException testMigrationExceptions(String s) {
    System.setProperty(ShamanProperties.LOCATION.getKey(), s);
    ShamanConfiguration.refresh();
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);
    ShamanMigrationException exception = null;

    // Act
    try {
      shaman.migrate();
    } catch (ShamanMigrationException e) {
      exception = e;
    }

    assertNotNull("Unexpected null exception", exception);
    return exception;
  }
  
}
