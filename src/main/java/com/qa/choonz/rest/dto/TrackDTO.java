package com.qa.choonz.rest.dto;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;

import java.util.Objects;

public class TrackDTO {

    private long id;
    private String name;
    private Album album;
    private Playlist playlist;
    private int duration;
    private String lyrics;
    private Artist artist;
    private Genre genre;

    public TrackDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackDTO)) return false;

        TrackDTO trackDTO = (TrackDTO) o;

        if (id != trackDTO.id) return false;
        if (duration != trackDTO.duration) return false;
        if (!Objects.equals(name, trackDTO.name)) return false;
        if (!Objects.equals(album, trackDTO.album)) return false;
        if (!Objects.equals(playlist, trackDTO.playlist)) return false;
        if (!Objects.equals(lyrics, trackDTO.lyrics)) return false;
        if (!Objects.equals(artist, trackDTO.artist)) return false;
        return Objects.equals(genre, trackDTO.genre);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (lyrics != null ? lyrics.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrackDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", album=" + album +
                ", playlist=" + playlist +
                ", duration=" + duration +
                ", lyrics='" + lyrics + '\'' +
                ", artist=" + artist +
                ", genre=" + genre +
                '}';
    }
}
