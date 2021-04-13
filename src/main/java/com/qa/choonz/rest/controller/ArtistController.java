package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@CrossOrigin
public class ArtistController {

    private ArtistService service;
    private ActiveSessions sessions;

    public ArtistController(ArtistService service, ActiveSessions sessions) {
        super();
        this.service = service;
        this.sessions = sessions;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtistDTO> create(@RequestBody Artist artist,
                                            @CookieValue(value = "SESSID") String sessID) {
        var created = this.service.create(artist, sessions.getSession(sessID));
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ArtistDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ArtistDTO> read(@PathVariable long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ArtistDTO> update(@RequestBody Artist artist, @PathVariable long id,
                                            @CookieValue(value = "SESSID") String sessID) {
        var updated = this.service.update(artist, id, sessions.getSession(sessID));
        return (updated != null) ?
                new ResponseEntity<>(updated, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ArtistDTO> delete(@PathVariable long id,
                                            @CookieValue(value = "SESSID") String sessID) {
        return this.service.delete(id, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
