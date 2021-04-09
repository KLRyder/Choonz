package com.qa.choonz.persistence.domain;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
    private Genre genre;

    @ManyToOne(targetEntity = Album.class, fetch = FetchType.LAZY)
    private Album album;

    // in seconds
    private int duration;

    private String lyrics;

    public Track() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Track(long id, @NotNull @Size(max = 100) String name, Album album, int duration,
            String lyrics) {
        super();
        this.id = id;
        this.name = name;
        this.album = album;
        this.duration = duration;
        this.lyrics = lyrics;
    }

    public Track(long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;

        Track track = (Track) o;

        if (duration != track.duration) return false;
        if (!Objects.equals(name, track.name)) return false;
        if (!Objects.equals(genre, track.genre)) return false;
        if (!Objects.equals(album, track.album)) return false;
        return Objects.equals(lyrics, track.lyrics);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (lyrics != null ? lyrics.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", album=" + album +
                ", duration=" + duration +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
