package com.qa.choonz.persistence.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(targetEntity = ArtistAlbumLink.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ArtistAlbumLink> artists;

    @NotNull
    private String cover;

    public Album() {
        super();
        tracks = Collections.emptyList();
        artists = Collections.emptyList();
    }

    public Album(long id, @NotNull @Size(max=100) String name, List<ArtistAlbumLink> artists, String cover) {
    	this.id = id;
        this.name = name;
        this.artists = artists;
        this.cover = cover;
        this.tracks = new ArrayList<>();
    }
    
    public Album(long id, @NotNull @Size(max = 100) String name, List<Track> tracks, List<ArtistAlbumLink> artists, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artists = artists;
        this.cover = cover;
    }

    public Album(long id) {
        this.id = id;
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

    public List<ArtistAlbumLink> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistAlbumLink> artists) {
        this.artists = artists;
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
        if (!Objects.equals(artists, album.artists)) return false;
        return Objects.equals(cover, album.cover);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (artists != null ? artists.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tracks=" + tracks +
                ", artist=" + artists +
                ", cover='" + cover + '\'' +
                '}';
    }
}
