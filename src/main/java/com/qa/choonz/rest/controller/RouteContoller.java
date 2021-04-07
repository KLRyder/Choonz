package com.qa.choonz.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
public class RouteContoller {
    @GetMapping(value = "/")
    public String index() {
        return "index.html";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "index.html";
    }

    @GetMapping(value = "/tracks")
    public String tracks(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8082/tracks/read");
        return "tracks.html";
    }

}
