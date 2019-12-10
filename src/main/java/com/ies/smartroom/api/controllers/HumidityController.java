package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.entities.Humidity;
import com.ies.smartroom.api.service.HumidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/humidity")
public class HumidityController extends SensorController {

    @Autowired
    public HumidityController(HumidityService humidityService) {
        super(humidityService);
    }
}

