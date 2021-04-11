package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sessions")
@CrossOrigin
public class SessionController {

    private SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createSession(@RequestBody @Valid UserDetails user) {
        if (service.authenticate(user) != null) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else return new ResponseEntity<>("fail", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDetails user) {
        return new ResponseEntity<>(this.service.create(user), HttpStatus.CREATED);
    }
}
