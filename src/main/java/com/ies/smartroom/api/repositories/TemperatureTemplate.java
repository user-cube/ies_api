package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Temperature;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TemperatureTemplate extends SensorTemplate {
    public TemperatureTemplate(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
