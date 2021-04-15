package com.qa.choonz.rest.dto;

import java.util.Set;

public class SearchResults {
    private Set<AlbumDTO> albums;
    private Set<ArtistDTO> artists;
    private Set<PlaylistDTO> playlists;
    private Set<GenreDTO> genres;
    private Set<TrackDTO> tracks;

    public Set<AlbumDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<AlbumDTO> albums) {
        this.albums = albums;
    }

    public Set<ArtistDTO> getArtists() {
        return artists;
    }

    public void setArtists(Set<ArtistDTO> artists) {
        this.artists = artists;
    }

    public Set<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    public Set<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(Set<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public SearchResults(Set<AlbumDTO> albums, Set<ArtistDTO> artists, Set<PlaylistDTO> playlists, Set<GenreDTO> genres, Set<TrackDTO> tracks) {
        this.albums = albums;
        this.artists = artists;
        this.playlists = playlists;
        this.genres = genres;
        this.tracks = tracks;
    }
}
