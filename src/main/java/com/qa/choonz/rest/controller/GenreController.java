package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@CrossOrigin
public class GenreController {

    private GenreService service;
    private ActiveSessions sessions;

    public GenreController(GenreService service, ActiveSessions sessions) {
        super();
        this.service = service;
        this.sessions = sessions;
    }

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> create(@RequestBody Genre genre,
                                           @CookieValue(value = "SESSID") String sessID) {
        var created = this.service.create(genre, sessions.getSession(sessID));
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/read")
    public ResponseEntity<List<GenreDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<GenreDTO> read(@PathVariable long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<GenreDTO> update(@RequestBody Genre genre, @PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        var updated = this.service.update(genre, id, sessions.getSession(sessID));
        return (updated != null) ?
                new ResponseEntity<>(updated, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenreDTO> delete(@PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        return this.service.delete(id, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
