package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.internal.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SensorRepository<T> extends MongoRepository<T, String> {
    List<Sensor> findByHome(long home);
}
