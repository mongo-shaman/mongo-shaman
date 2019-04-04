package io.github.mongoshaman.core.configuration;

import java.util.Optional;

/**
 * Shaman properties.
 */
public enum ShamanDefaultProperties {
  /**
   * Database name.
   */
  DATABASE_NAME(ShamanProperties.DATABASE_NAME, String.class, null),

  /**
   * Connection string.
   */
  CONNECTION_STRING(ShamanProperties.CONNECTION_STRING, String.class, null),

  /**
   * Database name.
   */
  COLLECTION_NAME(ShamanProperties.COLLECTION_NAME, String.class, "shaman"),

  /**
   * Location of migration files.
   */
  LOCATION(ShamanProperties.LOCATION, String.class, "db/migration/"),

  VERBOSE(ShamanProperties.VERBOSE, Boolean.class, false);

  private String key;
  private Class<?> aClass;
  private Object defaultValue;

  <T> ShamanDefaultProperties(final String key, final Class<T> aClass, final T defaultValue) {
    this.key = key;
    this.aClass = aClass;
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
  public <T> T getValue() {
    return (T) Optional.ofNullable((Object) System.getProperty(this.key)).orElse(defaultValue);
  }

}
