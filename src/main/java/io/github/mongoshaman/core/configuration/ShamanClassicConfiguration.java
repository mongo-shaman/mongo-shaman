package io.github.mongoshaman.core.configuration;

import java.util.Objects;

/**
 * The type Shaman configuration.
 */
public class ShamanClassicConfiguration implements ShamanConfiguration {

  private static ShamanConfiguration singleton;

  /** The database name. */
  private String databaseName;

  /** The Collection name.*/
  private String collectionName;

  /** The migration files. */
  private String location;

  /** Instantiates a new Shaman configuration. */
  private ShamanClassicConfiguration() {
    this.databaseName = ShamanDefaultProperties.DATABASE_NAME.getValue();
    this.collectionName = ShamanDefaultProperties.COLLECTION_NAME.getValue();
    this.location = ShamanDefaultProperties.LOCATION.getValue();
  }

  public static void refresh() {
    singleton = null;
  }

  public static ShamanConfiguration readConfiguration() {
    if(Objects.isNull(singleton)) {
      singleton = new ShamanClassicConfiguration();
    }
    return singleton;
  }

  @Override public String getDatabaseName() {
    return this.databaseName;
  }

  @Override public String getCollectionName() {
    return this.collectionName;
  }

  @Override public String getLocation() {
    return this.location;
  }
}
