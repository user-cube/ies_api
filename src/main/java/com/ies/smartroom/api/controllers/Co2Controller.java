package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.service.Co2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/co2")
public class Co2Controller extends SensorController {

    @Autowired
    public Co2Controller(Co2Service co2Service) {     super(co2Service);   }

    @Override
    @RequestMapping(value = "/averageDay", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageByDay(String day, Authentication authentication,Class type) {
        return super.getAverageByDay(day, authentication, Co2.class);
    }

    @Override
    public Mono<ResponseEntity<?>> AverageDate(String from, String to, Authentication authentication,Class type) {
        return super.AverageDate(from, to, authentication,Co2.class);
    }

    @Override
    public Mono<ResponseEntity<?>> getAverageOfLastWeek(Authentication authentication,Class type) {
        return super.getAverageOfLastWeek(authentication,Co2.class);
    }

    @Override
    public Mono<ResponseEntity<?>> getAverageOfToday(Authentication authentication,Class type) {
        return super.getAverageOfToday(authentication,Co2.class);
    }
}
