package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.rest.dto.SearchResults;
import com.qa.choonz.rest.mapper.*;
import com.qa.choonz.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    private TrackService trackService;
    private AlbumService albumService;
    private ArtistService artistService;
    private PlaylistService playlistService;
    private GenreService genreService;
    private TrackMapper trackMapper;
    private AlbumMapper albumMapper;
    private ArtistMapper artistMapper;
    private PlaylistMapper playlistMapper;
    private GenreMapper genreMapper;

    public SearchController(TrackService trackService, AlbumService albumService, ArtistService artistService, PlaylistService playlistService, GenreService genreService, TrackMapper trackMapper, AlbumMapper albumMapper, ArtistMapper artistMapper, PlaylistMapper playlistMapper, GenreMapper genreMapper) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.genreService = genreService;
        this.trackMapper = trackMapper;
        this.albumMapper = albumMapper;
        this.artistMapper = artistMapper;
        this.playlistMapper = playlistMapper;
        this.genreMapper = genreMapper;
    }


    @GetMapping()
    public ResponseEntity<SearchResults> search(@RequestParam String term, @RequestParam boolean strict) {
        Set<Album> albums = new HashSet<>();
        Set<Track> tracks = new HashSet<>();

        Set<Artist> artists = new HashSet<>(artistService.read(term));
        if (!strict) {
            // add albums by matching artists
            for (Artist artist : artists) {
                for (ArtistAlbumLink album : artist.getAlbums()) {
                    albums.add(album.getAlbum());
                }
            }
        }

        albums.addAll(albumService.read(term));
        if (!strict) {
            for (Album album : albums) {
                tracks.addAll(album.getTracks());
            }
        }

        Set<Genre> genres = new HashSet<>(genreService.read(term));
        if (!strict) {
            for (Genre genre : genres) {
                for (Track track : genre.getTracks()) {
                    tracks.add(track);
                    albums.add(track.getAlbum());
                }
            }
        }

        tracks.addAll(trackService.read(term));
        Set<Playlist> playlists = new HashSet<>(playlistService.read(term));

        return new ResponseEntity<>(
                new SearchResults(
                        albums.stream().map(albumMapper::mapToDeepDTO).collect(Collectors.toSet()),
                        artists.stream().map(artistMapper::mapToDeepDTO).collect(Collectors.toSet()),
                        playlists.stream().map(playlistMapper::mapToDeepDTO).collect(Collectors.toSet()),
                        genres.stream().map(genreMapper::mapToDeepDTO).collect(Collectors.toSet()),
                        tracks.stream().map(trackMapper::mapToDeepDTO).collect(Collectors.toSet())),
                HttpStatus.OK);
    }
}