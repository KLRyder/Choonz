package com.qa.choonz.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
    public String tracks() {
        return "tracks.html";
    }
    
    @GetMapping(value = "/albums")
    public String albums() {
        return "albums.html";
    }
    
    @GetMapping(value = "/artists")
    public String artists() {
        return "artists.html";
    }
    
    @GetMapping(value = "/genres")
    public String genres() {
        return "genres.html";
    }
    
    @GetMapping(value = "/hotArtists")
    public String hArtists() {
        return "hotArtists.html";
    }
    
    @GetMapping(value = "/hotPlaylists")
    public String hplaylists() {
        return "hotPlaylists.html";
    }
    
    @GetMapping(value = "/hotTracks")
    public String hTracks() {
        return "hotTracks.html";
    }
    
    @GetMapping(value = "/login")
    public String login() {
        return "login.html";
    }

    @GetMapping(value = "/playlists")
    public String playlists() {
        return "playlists.html";
    }
    
    @GetMapping(value = "/searchResponse")
    public String searchResponse() {
        return "searchResponse.html";
    }
    
    @GetMapping(value = "/error")
    public String error() {
        return "404.html";
    }
}
