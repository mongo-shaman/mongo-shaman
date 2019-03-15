package io.github.mongoshaman.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

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
    return new Shaman(mongoClient);
  }


  /**
   * Gets a Shaman instance.
   *
   * @param connectionString the connection string to MongoDb
   * @return the Shaman instance
   */
  public static Shaman getInstance(final String connectionString) {
    final MongoClient mongoClient = MongoClients.create(connectionString);
    return new Shaman(mongoClient);
  }
}
