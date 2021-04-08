package com.qa.choonz.persistence.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @ManyToOne
    private Album album;

    @ManyToOne
    private Playlist playlist;

    // in seconds
    private int duration;

    private String lyrics;

    public Track() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Track(long id, @NotNull @Size(max = 100) String name, Album album, Playlist playlist, int duration,
            String lyrics) {
        super();
        this.id = id;
        this.name = name;
        this.album = album;
        this.playlist = playlist;
        this.duration = duration;
        this.lyrics = lyrics;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;

        Track track = (Track) o;

        if (duration != track.duration) return false;
        if (!Objects.equals(getName(), track.getName())) return false;
        if (!Objects.equals(getAlbum(), track.getAlbum())) return false;
        if (!Objects.equals(getPlaylist(), track.getPlaylist())) return false;
        return Objects.equals(getLyrics(), track.getLyrics());
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
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
                ", playlist=" + playlist +
                ", duration=" + duration +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
