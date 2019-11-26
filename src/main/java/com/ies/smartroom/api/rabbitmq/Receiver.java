package com.ies.smartroom.api.rabbitmq;

import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    public MongoWorker worker;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("amqp://rniqsthq:LTcMEkPr-dN63CAETa0x5jDOM2S_Zm1H@gopher.rmq.cloudamqp.com/rniqsthq");
        connectionFactory.setUsername("rniqsthq");
        connectionFactory.setPassword("LTcMEkPr-dN63CAETa0x5jDOM2S_Zm1H");
        connectionFactory.setVirtualHost("rniqsthq");
        connectionFactory.setPort(1883);
        return connectionFactory;
    }

    @RabbitListener(queues = "gen_ata")
    public void processOrder(String data) {
        Document doc = Document.parse(data);
        if (doc.containsKey("temp"))
            worker.insertTemperature(doc);
        else if (doc.containsKey("co2(ppm)"))
            worker.insertCo2(doc);
        else if (doc.containsKey("user"))
            worker.insertAccess(doc);
    }
}
