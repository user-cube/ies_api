package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.entities.internal.AddCredential;
import com.ies.smartroom.api.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> acs = accessService.today(home);
            return Mono.just(ResponseEntity.ok(acs));
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/getByDateRange", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessByDateRange(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> acs = accessService.getByDateRange(from, to, home);
            return Mono.just(ResponseEntity.ok(acs));
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }

    @RequestMapping(value = "/lastWeek", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            List<?> acs = accessService.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(acs)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
    @RequestMapping(value = "/unauthorizedAcess", method = RequestMethod.GET)
    public Mono<ResponseEntity> getUnauthorizedAcess(Authentication authentication){
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
        long home = Long.valueOf(String.valueOf(obj));
        List<?> acs = accessService.getUnauthorizedAcess(home);
        return Mono.just(
                ResponseEntity.ok(acs)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
    @RequestMapping(value = "/addCredential", method = RequestMethod.POST)
    public Mono<ResponseEntity> addCredential(Authentication authentication, @RequestBody AddCredential addCcredential){
        if (authentication.isAuthenticated()) {
            Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
            long home = Long.valueOf(String.valueOf(obj));
            Credential credential = new Credential(null, home, addCcredential.getUser(), addCcredential.getCartId());
            Credential saveCredential = accessService.SaveCredential(credential);
            return Mono.just(
                    ResponseEntity.ok(saveCredential)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.OK)
        );
    }
}
