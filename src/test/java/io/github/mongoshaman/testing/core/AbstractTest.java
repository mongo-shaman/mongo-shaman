package io.github.mongoshaman.testing.core;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.github.mongoshaman.core.configuration.ShamanDefaultProperties;
import io.github.mongoshaman.core.configuration.ShamanProperties;

public abstract class AbstractTest {
  private static final String DB_NAME = "shaman-test";
  private static final String HOST_NAME = "localhost";
  private static final int PORT = 12345;


  protected static final String CONNECTION_STRING = "mongodb://"+HOST_NAME+":"+PORT+"/" + DB_NAME;
  protected MongoClient mongoClient;

  private MongodExecutable mongodExe;
  private MongodProcess mongod;

  @Before
  public void beforeEach() throws Exception {
    MongodStarter starter = MongodStarter.getDefaultInstance();
    String bindIp = "localhost";
    int port = 12345;
    IMongodConfig mongodConfig = new MongodConfigBuilder()
      .version(Version.Main.PRODUCTION)
      .net(new Net(bindIp, port, Network.localhostIsIPv6()))
      .build();
    this.mongodExe = starter.prepare(mongodConfig);
    this.mongod = mongodExe.start();
    this.mongoClient = MongoClients.create(CONNECTION_STRING);
  }

  @After
  public void afterEach() throws Exception {
    if (this.mongod != null) {
      this.mongod.stop();
      this.mongodExe.stop();
    }
  }

  protected static void globalInit() {
    setDatabaseNameProperty();
  }

  protected static void setDatabaseNameProperty() {
    System.setProperty(ShamanDefaultProperties.DATABASE_NAME.getKey(), DB_NAME);
  }

  protected MongoDatabase getDatabase() {
    final MongoCollection<Document> collection =
      mongoClient.getDatabase(ShamanDefaultProperties.DATABASE_NAME.getValue()).getCollection("shaman");

    return mongoClient.getDatabase(ShamanDefaultProperties.DATABASE_NAME.getValue());
  }

  protected List<Document> getInternalCollectionDocuments() {
    return getInternalCollection().find().into(new ArrayList<>());
  }

  protected MongoCollection<Document> getInternalCollection() {
    return getCollection(ShamanDefaultProperties.COLLECTION_NAME.getValue());
  }

  protected MongoCollection<Document> getCollection(final String collectionName) {
    return getDatabase().getCollection(collectionName);
  }

  protected void assertInternalDocuments(int i) {
    Assert.assertEquals("Unexpected documents count", i, getInternalCollectionDocuments().size());
  }

  protected void setLocation(String s) {
    final URL systemResource = ClassLoader.getSystemResource(s);
    System.out.println(systemResource.toString());
    String location = systemResource.toString();
    location = location.substring(location.indexOf(":") + 2);
    System.setProperty(ShamanProperties.LOCATION, location);
  }
}
