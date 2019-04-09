package io.github.mongoshaman.core.configuration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

public class ShamanFluentConfigurationTest {

  @Test
  public void test() {
    // Arrange
    final ShamanFluentConfiguration configuration = new ShamanFluentConfiguration();

    // Act
    configuration.database("test-database").collectionName("test-collection").location("test-location");

    // Assert
    assertEquals("Unexpected database", "test-database", configuration.getDatabaseName());
    assertEquals("Unexpected collection name", "test-collection", configuration.getCollectionName());
    assertEquals("Unexpected location", "test-location", configuration.getLocation());

  }

}
