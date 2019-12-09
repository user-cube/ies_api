package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CredentialRepository extends MongoRepository<Credential,String> {
    @Query(value="{'home':?0,'cart_id':?1 }")
    List<Credential> findByCredetialAndHome(long home, String cart_id);
}
