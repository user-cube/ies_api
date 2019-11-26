package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Access;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface    AccessRepository extends MongoRepository<Access,String> {
    @Query(value="{'home':?0,'timestamp':{$gte:?1,$lt:?2}}")
    List<Access> findByDate(long home,String datenow,String datenext);
    List<Access> findByHome(long home);
}
