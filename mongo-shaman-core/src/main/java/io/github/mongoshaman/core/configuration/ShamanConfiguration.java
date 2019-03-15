package io.github.mongoshaman.core.configuration;

import java.util.Objects;

/**
 * The type Shaman configuration.
 */
public class ShamanConfiguration {

  private static ShamanConfiguration singleton;

  /** The database name. */
  private String databaseName;

  /** The Collection name.*/
  public String collectionName;

  /** The migration files. */
  private String location;

  /** Instantiates a new Shaman configuration. */
  private ShamanConfiguration() {
    this.databaseName = ShamanProperties.DATABASE_NAME.getValue();
    this.collectionName = ShamanProperties.COLLECTION_NAME.getValue();
    this.location = ShamanProperties.LOCATION.getValue();
  }

  public static void refresh() {
    singleton = null;
  }

  public static ShamanConfiguration get() {
    if(Objects.isNull(singleton)) {
      singleton = new ShamanConfiguration();
    }
    return singleton;
  }

  public String getDatabaseName() {
    return this.databaseName;
  }

  public String getCollectionName() {
    return this.collectionName;
  }

  public String getLocation() {
    return this.location;
  }
}
