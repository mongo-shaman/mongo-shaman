package io.github.mongoshaman.core.configuration;

/**
 * Shaman properties.
 */
public final class ShamanProperties {

  private static final String PREFIX = "shaman.";

  /**
   * Database name.
   */
  public static final String DATABASE_NAME = PREFIX + "database.name";

  /**
   * Connection string name.
   */
  public static final String CONNECTION_STRING = PREFIX + "database.uri";

  /**
   * Internal collection name.
   */
  public static final String COLLECTION_NAME = PREFIX + "internal.collection.name";

  /**
   * Location of migration files.
   */
  public static final String LOCATION = PREFIX + "location";

  /**
   * Verbose property.
   */
  public static final String VERBOSE = PREFIX + "verbose";

  private ShamanProperties() {}
}
