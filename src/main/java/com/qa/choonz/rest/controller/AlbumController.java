package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.service.AlbumService;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@CrossOrigin
public class AlbumController {

    private AlbumService service;
    private ActiveSessions sessions;

    public AlbumController(AlbumService service, ActiveSessions sessions) {
        super();
        this.service = service;
        this.sessions = sessions;
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumDTO> create(@RequestBody Album album,
                                           @CookieValue(value = "SESSID") String sessID) {
        var created = this.service.create(album, sessions.getSession(sessID));
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/read")
    public ResponseEntity<List<AlbumDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }


    @GetMapping("/read/{id}")
    public ResponseEntity<AlbumDTO> read(@PathVariable long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AlbumDTO> update(@RequestBody Album album, @PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        var updated = this.service.update(album, id, sessions.getSession(sessID));
        return (updated != null) ?
                new ResponseEntity<>(updated, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AlbumDTO> delete(@PathVariable long id,
                                           @CookieValue(value = "SESSID") String sessID) {
        return this.service.delete(id, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/add/{albumID}/{artistID}")
    public ResponseEntity<AlbumDTO> addArtist(@PathVariable long albumID, @PathVariable long artistID,
                                              @CookieValue(value = "SESSID") String sessID) {
        return this.service.add(albumID, artistID, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/remove/{albumID}/{artistID}")
    public ResponseEntity<AlbumDTO> removeArtist(@PathVariable long albumID, @PathVariable long artistID,
                                                 @CookieValue(value = "SESSID") String sessID) {
        return this.service.remove(albumID, artistID, sessions.getSession(sessID)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
