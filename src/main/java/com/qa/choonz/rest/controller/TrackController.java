package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@CrossOrigin
public class TrackController {

    private TrackService service;
    private ActiveSessions sessions;

    public TrackController(TrackService service, ActiveSessions sessions) {
        super();
        this.service = service;
        this.sessions = sessions;
    }

    @PostMapping("/create")
    public ResponseEntity<TrackDTO> create(@RequestBody Track track,
                                           @CookieValue(value = "SESSID") String sessID) {
        var created = this.service.create(track, sessions.getSession(sessID));
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/read")
    public ResponseEntity<List<TrackDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<TrackDTO> read(@PathVariable long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<TrackDTO> update(@RequestBody Track track, @PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        var updated = this.service.update(track, id, sessions.getSession(sessID));
        return (updated != null) ?
                new ResponseEntity<>(updated, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<TrackDTO> delete(@PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        return this.service.delete(id, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
