package com.ies.smartroom.api.service;

import com.ies.smartroom.api.repositories.HumidityRepository;
import com.ies.smartroom.api.repositories.HumidityTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumidityService  extends SensorService {
    @Autowired
    public HumidityService(HumidityRepository humidityRepository, HumidityTemplate template) {
        super(humidityRepository,template);
    }
}
