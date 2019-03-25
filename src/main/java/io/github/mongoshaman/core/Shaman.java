package io.github.mongoshaman.core;

import com.mongodb.client.MongoClient;

import io.github.mongoshaman.core.commands.CleanCommand;
import io.github.mongoshaman.core.commands.MigrateCommand;
import io.github.mongoshaman.core.configuration.ShamanConfiguration;

public class Shaman {

  private MongoClient mongoClient;

  private ShamanConfiguration configuration = ShamanConfiguration.get();

  Shaman(final MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public void migrate() {
    new MigrateCommand(mongoClient, configuration).run();
  }

  public void clean() {
    new CleanCommand(mongoClient, configuration).run();
  }
}
