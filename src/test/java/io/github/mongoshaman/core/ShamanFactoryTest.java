package io.github.mongoshaman.core;

import io.github.mongoshaman.core.configuration.ShamanClassicConfiguration;
import io.github.mongoshaman.testing.core.AbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShamanFactoryTest extends AbstractTest {

  @BeforeClass
  public static void globalInit() {
    AbstractTest.globalInit();
  }

  @Test
  public void getInstance_MongoClient() {
    // Arrange

    // Act
    final Shaman shaman = ShamanFactory.getInstance(mongoClient);

    // Asset
    Assert.assertNotNull(shaman);
  }

  @Test
  public void getInstance_MongoClient_Config() {
    // Arrange

    // Act
    final Shaman shaman = ShamanFactory.getInstance(mongoClient, ShamanClassicConfiguration.readConfiguration());

    // Asset
    Assert.assertNotNull(shaman);
  }

  @Test
  public void getInstance_ConnectionString() {
    // Arrange

    // Act
    final Shaman shaman = ShamanFactory.getInstance(CONNECTION_STRING);

    // Asset
    Assert.assertNotNull(shaman);
  }

  @Test
  public void getInstance_ConnectionString_Config() {
    // Arrange

    // Act
    final Shaman shaman = ShamanFactory.getInstance(CONNECTION_STRING, ShamanClassicConfiguration.readConfiguration());

    // Asset
    Assert.assertNotNull(shaman);
  }
}
