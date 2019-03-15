package io.github.mongoshaman.testing.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.mongoshaman.core.configuration.ShamanProperties;
import org.bson.Document;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTest {
  protected static final String TEST = "shaman-test";
  protected static final String CONNECTION_STRING = "mongodb://localhost:27017/" + TEST + "?replicaSet=rs0";

  protected MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);

  protected static void globalInit() {
    setDatabaseNameProperty();
  }

  protected static void setDatabaseNameProperty() {
    System.setProperty(ShamanProperties.DATABASE_NAME.getKey(), TEST);
  }

  protected MongoDatabase getDatabase() {
    final MongoCollection<Document> collection =
      mongoClient.getDatabase(ShamanProperties.DATABASE_NAME.getValue()).getCollection("shaman");

    return mongoClient.getDatabase(ShamanProperties.DATABASE_NAME.getValue());
  }

  protected List<Document> getInternalCollectionDocuments() {
    return getInternalCollection().find().into(new ArrayList<>());
  }

  protected MongoCollection<Document> getInternalCollection() {
    return getCollection(ShamanProperties.COLLECTION_NAME.getValue());
  }

  protected MongoCollection<Document> getCollection(final String collectionName) {
    return getDatabase().getCollection(collectionName);
  }

  protected void assertInternalDocuments(int i) {
    Assert.assertEquals("Unexpected documents count", i, getInternalCollectionDocuments().size());
  }
}
