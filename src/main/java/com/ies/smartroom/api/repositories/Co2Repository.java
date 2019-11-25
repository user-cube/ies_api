package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Co2;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Co2Repository extends MongoRepository<Co2,String> {
    List<Co2> findByDate(String date);
    List<Co2> findByHome(long home);
}
