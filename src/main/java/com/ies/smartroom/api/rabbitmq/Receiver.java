package com.ies.smartroom.api.rabbitmq;

import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.service.AccessService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.bson.Document;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    @Autowired
    private MongoWorker worker;
    @Autowired
    private AccessService accessService;

    public static String ACCESS_GRANTED = "Acesso permitido";
    public static String ACCESS_DENIED = "Acesso negado";
    private Channel channel;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqp://rniqsthq:oEncQ4eBhISeQn-BAxkyJ7OHyRfgR9BN@gopher.rmq.cloudamqp.com/rniqsthq");
        connectionFactory.setRequestedHeartBeat(30);
        connectionFactory.setConnectionTimeout(30000);
        channel = connectionFactory.createConnection().createChannel(true);
        return connectionFactory;
    }


    @RabbitListener(queues = "gen_data")
    public void processOrder(String data) {

        Document doc = Document.parse(data);
        if (doc.containsKey("temp"))
            worker.insertTemperature(doc);
        else if (doc.containsKey("co2(ppm)"))
            worker.insertCo2(doc);
        else if (doc.containsKey("humidity")) {
            worker.insertHumidity(doc);
        } else if (doc.containsKey("origin")) {
            String response = writeCredentials(doc);
            try {
                channel.basicPublish("",
                        "gen_data",
                        new AMQP.BasicProperties.Builder().correlationId(java.util.UUID.randomUUID().toString()).build(),
                        response.getBytes()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String writeCredentials(Document doc) {
        Credential credential = accessService.checkCredentials(Long.valueOf((String) doc.get("home")), (String) doc.get("cart_id"));
        String open;
        if (credential == null) {
            doc.append("user", null);
            doc.append("action", ACCESS_DENIED);
            open = ACCESS_DENIED;
        } else {
            doc.append("user", credential.getUser());
            doc.append("action", ACCESS_GRANTED);
            open = ACCESS_GRANTED;
        }
        worker.insertAccess(doc);
        return open;
    }


}
