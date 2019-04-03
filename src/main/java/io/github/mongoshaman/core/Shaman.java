package io.github.mongoshaman.core;

import com.mongodb.client.MongoClient;
import io.github.mongoshaman.core.commands.CleanCommand;
import io.github.mongoshaman.core.commands.MigrateCommand;
import io.github.mongoshaman.core.configuration.ShamanClassicConfiguration;
import io.github.mongoshaman.core.configuration.ShamanConfiguration;

/**
 * Shaman.
 *
 * Change management for MongoDB using mongo-shell scripts.
 *
 *
 * @author antoniovizuete
 */
public class Shaman {

  private MongoClient mongoClient;

  private ShamanConfiguration configuration;

  Shaman(final MongoClient mongoClient) {
    this.mongoClient = mongoClient;
    configuration = ShamanClassicConfiguration.readConfiguration();
  }

  Shaman(final MongoClient mongoClient, ShamanConfiguration configuration) {
    this.mongoClient = mongoClient;
    this.configuration = configuration;
  }

  public void migrate() {
    new MigrateCommand(mongoClient, configuration).run();
  }

  public void clean() {
    new CleanCommand(mongoClient, configuration).run();
  }
}
