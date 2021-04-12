package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.rest.dto.UserDetailsDTO;
import com.qa.choonz.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> createSession(@RequestBody @Valid UserDetails user, HttpServletResponse response) {
        var sessionId = service.authenticate(user);
        if (sessionId != null) {
            Cookie sessionCookie = new Cookie("SESSID", sessionId);
            sessionCookie.setHttpOnly(false);
            sessionCookie.setSecure(true);
            sessionCookie.setMaxAge(60*60);
            sessionCookie.setDomain("localhost");
            response.addCookie(sessionCookie);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else return new ResponseEntity<>("fail", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDetails user) {
        return new ResponseEntity<>(this.service.create(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDetailsDTO> getUsername(@CookieValue(value = "SESSID", required = true) String sessID) {
        return new ResponseEntity<>(service.userDetails(sessID), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> endSession(@CookieValue(value = "SESSID", required = true) String sessID){
        Cookie sessionCookie = new Cookie("SESSID", null);
        sessionCookie.setMaxAge(0);
        Cookie usernameCookie = new Cookie("U_NAME", null);
        usernameCookie.setMaxAge(0);
        return new ResponseEntity<>(service.logout(sessID),HttpStatus.OK);
    }
}
