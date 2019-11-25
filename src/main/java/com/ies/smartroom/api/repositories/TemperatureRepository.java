package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TemperatureRepository extends MongoRepository<Temperature,String> {
    List<Temperature> findByTimestamp(String timestamp);
    List<Temperature> findByHome(long home);
}
