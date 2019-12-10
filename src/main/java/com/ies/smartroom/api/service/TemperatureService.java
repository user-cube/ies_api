package com.ies.smartroom.api.service;

import com.ies.smartroom.api.repositories.TemperatureRepository;
import com.ies.smartroom.api.repositories.TemperatureTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService extends SensorService {
    @Autowired
    public TemperatureService(TemperatureRepository temperatureRepository, TemperatureTemplate template) {
        super(temperatureRepository,template);
    }
}