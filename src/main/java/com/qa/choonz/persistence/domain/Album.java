package com.qa.choonz.persistence.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Track> tracks;

    @ManyToOne(targetEntity = Artist.class, fetch = FetchType.LAZY)
    private Artist artist;

    @NotNull
    private String cover;

    public Album() {
        super();
        tracks = Collections.emptyList();
    }

    public Album(long id, @NotNull @Size(max=100) String name, Artist artist, String cover) {
    	this.id = id;
        this.name = name;
        this.artist = artist;
        this.cover = cover;
        this.tracks = new ArrayList<>();
    }
    
    public Album(long id, @NotNull @Size(max = 100) String name, List<Track> tracks, Artist artist, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artist = artist;
        this.cover = cover;
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;

        Album album = (Album) o;

        if (!Objects.equals(name, album.name)) return false;
        if (!Objects.equals(tracks, album.tracks)) return false;
        if (!Objects.equals(artist, album.artist)) return false;
        return Objects.equals(cover, album.cover);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tracks=" + tracks +
                ", artist=" + artist +
                ", cover='" + cover + '\'' +
                '}';
    }
}
