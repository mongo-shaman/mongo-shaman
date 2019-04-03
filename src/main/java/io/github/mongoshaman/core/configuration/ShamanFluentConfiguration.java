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
    return Optional.ofNullable(this.databaseName).orElseGet(ShamanProperties.DATABASE_NAME::getValue);
  }

  public String getCollectionName() {
    return Optional.ofNullable(this.collectionName).orElseGet(ShamanProperties.COLLECTION_NAME::getNullSafeValue);
  }

  public String getLocation() {
    return Optional.ofNullable(this.location).orElseGet(ShamanProperties.LOCATION::getNullSafeValue);
  }

  public ShamanFluentConfiguration setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
    return this;
  }

  public ShamanFluentConfiguration setCollectionName(String collectionName) {
    this.collectionName = collectionName;
    return this;
  }

  public ShamanFluentConfiguration setLocation(String location) {
    this.location = location;
    return this;
  }
}
