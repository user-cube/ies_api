package com.ies.smartroom.api.service;

import com.ies.smartroom.api.repositories.Co2Repository;
import com.ies.smartroom.api.repositories.Co2Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Co2Service extends SensorService {
    @Autowired
    public Co2Service(Co2Repository co2Repository, Co2Template co2Template) {
        super(co2Repository,co2Template);
    }
}