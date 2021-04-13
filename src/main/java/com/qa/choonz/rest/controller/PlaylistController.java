package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.service.PlaylistService;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin
public class PlaylistController {

    private PlaylistService service;
    private ActiveSessions sessions;

    public PlaylistController(PlaylistService service, ActiveSessions sessions) {
        super();
        this.service = service;
        this.sessions = sessions;
    }

    @PostMapping("/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody Playlist playlist,
                                              @CookieValue(value = "SESSID") String sessID) {
        var created = this.service.create(playlist, sessions.getSession(sessID));
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/read")
    public ResponseEntity<List<PlaylistDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<PlaylistDTO> read(@PathVariable long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PlaylistDTO> update(@RequestBody Playlist playlist, @PathVariable long id,
                                              @CookieValue(value = "SESSID") String sessID) {
        var updated = this.service.update(playlist, id, sessions.getSession(sessID));
        return (updated != null) ?
                new ResponseEntity<>(updated, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlaylistDTO> delete(@PathVariable long id,
                                              @CookieValue(value = "SESSID") String sessID) {
        return this.service.delete(id, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/add/{id}/{track}")
    public ResponseEntity<PlaylistDTO> addTrack(@PathVariable long id, @PathVariable long track,
                                                @CookieValue(value = "SESSID") String sessID) {
        return this.service.add(id, track, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/remove/{id}/{track}")
    public ResponseEntity<PlaylistDTO> removeTrack(@PathVariable long id, @PathVariable long track,
                                                   @CookieValue(value = "SESSID") String sessID) {
        return this.service.remove(id, track, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
