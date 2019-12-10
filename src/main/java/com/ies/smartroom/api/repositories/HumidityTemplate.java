package com.ies.smartroom.api.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HumidityTemplate extends SensorTemplate {
    public HumidityTemplate(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
