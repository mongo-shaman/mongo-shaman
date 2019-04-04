package io.github.mongoshaman.core.configuration;

import java.util.Optional;

/**
 * The type Shaman configuration.
 */
public class ShamanFluentConfiguration implements ShamanConfiguration {

  /** The database name. */
  private String databaseName;

  /** The Collection name.*/
  private String collectionName;

  /** The migration files. */
  private String location;

  public String getDatabaseName() {
    return Optional.ofNullable(this.databaseName).orElseGet(ShamanDefaultProperties.DATABASE_NAME::getValue);
  }

  public String getCollectionName() {
    return Optional.ofNullable(this.collectionName).orElseGet(ShamanDefaultProperties.COLLECTION_NAME::getValue);
  }

  public String getLocation() {
    return Optional.ofNullable(this.location).orElseGet(ShamanDefaultProperties.LOCATION::getValue);
  }

  public ShamanFluentConfiguration database(String databaseName) {
    this.databaseName = databaseName;
    return this;
  }

  public ShamanFluentConfiguration collectionName(String collectionName) {
    this.collectionName = collectionName;
    return this;
  }

  public ShamanFluentConfiguration location(String location) {
    this.location = location;
    return this;
  }
}
