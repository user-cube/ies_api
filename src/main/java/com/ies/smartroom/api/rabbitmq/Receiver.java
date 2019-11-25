package com.ies.smartroom.api.rabbitmq;

import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    public MongoWorker worker;

    public Receiver(){
        this.worker = new MongoWorker();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("amqp://rniqsthq:LTcMEkPr-dN63CAETa0x5jDOM2S_Zm1H@gopher.rmq.cloudamqp.com/rniqsthq",1883);
    }

    @RabbitListener(queues = "hello")
    @RabbitListener(queues = "hello1")
    @RabbitListener(queues = "hello3")
    public void processOrder(String data) {
        System.out.println("Received "+ data);
        Document doc = Document.parse(data);
        if (doc.containsKey("temp"))
            worker.insertTemperature(doc);
        else if (doc.containsKey("co2(ppm)"))
            worker.insertCo2(doc);
        else if (doc.containsKey("user"))
            worker.insertAccess(doc);
    }
}