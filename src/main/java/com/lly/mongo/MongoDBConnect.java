package com.lly.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnect {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("47.98.196.59", 27017);
        MongoDatabase test = client.getDatabase("test");
        MongoCollection<Document> collection = test.getCollection("col");
//        FindIterable<Document> documents = collection.find();
        BasicDBObject object = new BasicDBObject();
        object.append("likes",new BasicDBObject("$gt",100));
        FindIterable<Document> documents = collection.find(object);
        for(Document document : documents){
            System.out.println(document);
        }
    }
}
