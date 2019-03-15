package io.github.mongoshaman.core.configuration;

import java.util.Optional;

import io.github.mongoshaman.core.exceptions.ShamanPropertiesException;

/**
 * Shaman properties.
 */
public enum ShamanProperties {
  /**
   * Database name.
   */
  DATABASE_NAME("database.name", null),

  /**
   * Database name.
   */
  COLLECTION_NAME("internal.collection.name", "shaman"),

  /**
   * Location of migration files.
   */
  LOCATION("location", "db/migration/");

  private static final String PREFIX = "shaman.";

  private String key;
  private String defaultValue;

  ShamanProperties(final String key, final String defaultValue) {
    this.key = PREFIX + key;
    this.defaultValue = defaultValue;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Gets property value.
   *
   * @return the value
   */
  public String getValue() {
    return Optional.ofNullable(System.getProperty(this.key, this.defaultValue))
        .orElseThrow(() -> new ShamanPropertiesException(this.key + " is not specified"));
  }

}
