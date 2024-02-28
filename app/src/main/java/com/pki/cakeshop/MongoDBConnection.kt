package com.pki.cakeshop

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object MongoDBConnection {
    //private const val CONNECTION_STRING = "mongodb+srv://jelenapancevski:cakeshop@cakeshop.nvid4wb.mongodb.net/"
    /*fun getMongoClient(): MongoClient {
        return MongoClients.create(CONNECTION_STRING)
    }*/
    // Connect to the MongoDB server
    val mongoClient = MongoClients.create("mongodb://localhost:27017")

    // Access a specific database
    val database: MongoDatabase = mongoClient.getDatabase("cakeshop")
    fun getData():MongoDatabase{
        return database;
    }

   /*

    // Access a specific collection within the database
    val collection: MongoCollection<Document> = database.getCollection("your_collection_name")

    // Insert a document into the collection
    val document = Document("key", "value")
    collection.insertOne(document)

    // Query documents from the collection
    val query = Document("key", "value")
    val result = collection.find(query)

// Process the query result
    result.forEach { println(it.toJson()) }

// Close the MongoDB client when done
    mongoClient.close()

    */
}