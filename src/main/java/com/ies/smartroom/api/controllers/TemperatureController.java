package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Temperature;
import com.ies.smartroom.api.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> temperature(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            return Mono.just(
                    ResponseEntity.ok(temperatureService.findAll(home))
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/getByDate/{date}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> temperatureByDate(@PathVariable String date, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> temps = temperatureService.getByDate(date, home);
            return Mono.just(
                    ResponseEntity.ok(temps)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> temperatureNow(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> temps = temperatureService.getNow(home);
            return Mono.just(
                    ResponseEntity.ok(temps)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
    @RequestMapping(value = "/getByDateRange", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> temperatureByDate(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> temps = temperatureService.getByDateRange(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(temps)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/lastWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> temperatureLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> temps = temperatureService.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(temps)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

}
