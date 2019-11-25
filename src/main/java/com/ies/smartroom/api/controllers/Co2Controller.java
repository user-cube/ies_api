package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.service.Co2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/co2")
public class Co2Controller {

    @Autowired
    private Co2Service co2Service;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> Co2(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            return Mono.just(
                    ResponseEntity.ok(co2Service.findAll(home))
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/getByDate/{date}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> Co2ByDate(@PathVariable String date, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = co2Service.getByDate(date, home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> Co2Now(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = co2Service.getNow(home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
    @RequestMapping(value = "/getByDateRange", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> Co2ByDate(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = co2Service.getByDateRange(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/lastWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> Co2LastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> co2s = co2Service.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(co2s)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

}
