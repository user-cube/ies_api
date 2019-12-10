package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.internal.Average;
import com.ies.smartroom.api.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
public abstract class SensorController {

    protected SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAll(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            return Mono.just(
                    ResponseEntity.ok(sensorService.findAll(home))
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/getByDay", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllByDay(@RequestParam String day, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = sensorService.getByDay(day, home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = sensorService.getNow(home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/getByDateRange", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> GetAllByDateRange(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = sensorService.getByDateRange(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/lastWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = sensorService.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/averageDate", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> AverageDate(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<Average> average = sensorService.getAverageDate(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/averageWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<Average>  average = sensorService.getAverageWeek(home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/averageDay", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageByDay(@RequestParam String day, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            Average average = sensorService.getAverageDay(day, home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
    @RequestMapping(value = "/averageToday", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            Average average = sensorService.getAverageToday(home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
}
