package io.github.mongoshaman.core.commands;

import com.mongodb.client.MongoClient;
import io.github.mongoshaman.core.configuration.ShamanConfiguration;
import io.github.mongoshaman.core.repositories.ShamanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanCommand implements Command {

  private static final Logger LOGGER = LoggerFactory.getLogger(CleanCommand.class);

  private ShamanRepository repository;

  public CleanCommand(final MongoClient mongoClient, final ShamanConfiguration configuration) {
    this.repository = new ShamanRepository(mongoClient, configuration);
  }

  @Override
  public void run() {
    LOGGER.info("Dropping database {}...", repository.getMongoDatabase().getName());
    repository.getMongoDatabase().drop();
    LOGGER.info("{} database has been dropped", repository.getMongoDatabase().getName());
  }

}
