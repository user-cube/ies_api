package com.ies.smartroom.api.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Co2Template extends SensorTemplate {

    public Co2Template(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
