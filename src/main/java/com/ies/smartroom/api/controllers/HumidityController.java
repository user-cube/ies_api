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

    @Override
    @RequestMapping(value = "/averageDay", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageByDay(String day, Authentication authentication, Class type) {
        return super.getAverageByDay(day, authentication, Humidity.class);
    }

    @Override
    @RequestMapping(value = "/averageDate", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> AverageDate(String from, String to, Authentication authentication,Class type) {
        return super.AverageDate(from, to, authentication,Humidity.class);
    }

    @Override
    @RequestMapping(value = "/averageWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfLastWeek(Authentication authentication,Class type) {
        return super.getAverageOfLastWeek(authentication,Humidity.class);
    }



    @Override
    @RequestMapping(value = "/averageToday", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfToday(Authentication authentication,Class type) {
        return super.getAverageOfToday(authentication,Humidity.class);
    }
}

