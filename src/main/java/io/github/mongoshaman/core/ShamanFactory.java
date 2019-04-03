package io.github.mongoshaman.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.mongoshaman.core.configuration.ShamanClassicConfiguration;
import io.github.mongoshaman.core.configuration.ShamanConfiguration;

/**
 * The type Shaman factory.
 */
public final class ShamanFactory {

  private ShamanFactory() {
    /* Avoid class instantiation. */
  }

  /**
   * Gets a Shaman instance.
   *
   * @param mongoClient the mongo client
   * @return the Shaman instance
   */
  public static Shaman getInstance(final MongoClient mongoClient) {
    return new Shaman(mongoClient, ShamanClassicConfiguration.readConfiguration());
  }

  public static Shaman getInstance(final MongoClient mongoClient, final ShamanConfiguration configuration) {
    return new Shaman(mongoClient, configuration);
  }


  /**
   * Gets a Shaman instance.
   *
   * @param connectionString the connection string to MongoDb
   * @return the Shaman instance
   */
  public static Shaman getInstance(final String connectionString) {
    return getInstance(MongoClients.create(connectionString));
  }

  public static Shaman getInstance(final String connectionString, final ShamanConfiguration configuration) {
    return getInstance(MongoClients.create(connectionString), configuration);
  }


}
