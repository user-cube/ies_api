package com.ies.smartroom.api.rabbitmq;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoWorker {

    @Value("${spring.data.mongodb.uri}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    private MongoCollection<Document> temperature;
    private MongoCollection<Document> co2;
    private MongoCollection<Document> access;

    public MongoWorker(){
        MongoClient mongoClient = new MongoClient("mongodb+srv://smart_user:W2Nx6xmAtnzK2Zxa@cluster0-wq6zj.mongodb.net/test?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("smartroom");
        temperature = database.getCollection("temperature");
        co2 = database.getCollection("co2");
        access = database.getCollection("access");
    }

    void insertTemperature(Document json){
        temperature.insertOne(json);
    }

    void insertCo2(Document json){
        co2.insertOne(json);
    }

    void insertAccess(Document json){
        access.insertOne(json);
    }
}
