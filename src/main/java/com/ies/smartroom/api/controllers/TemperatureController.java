package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.entities.Temperature;
import com.ies.smartroom.api.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/temperature")
public class TemperatureController extends SensorController {

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        super(temperatureService);
    }

    @Override
    @RequestMapping(value = "/averageDay", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> AverageDay(String day, Authentication authentication, Class type) {
        return super.AverageDay(day, authentication, Temperature.class);
    }

    @Override
    public Mono<ResponseEntity<?>> AverageDate(String from, String to, Authentication authentication,Class type) {
        return super.AverageDate(from, to, authentication,Temperature.class);
    }

    @Override
    public Mono<ResponseEntity<?>> AverageWeek(Authentication authentication,Class type) {
        return super.AverageWeek(authentication, Temperature.class);
    }

    @Override
    public Mono<ResponseEntity<?>> AverageToday(Authentication authentication,Class type) {
        return super.AverageToday(authentication,Temperature.class);
    }

}
