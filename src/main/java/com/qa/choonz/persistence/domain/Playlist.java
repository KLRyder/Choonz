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
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 500)
    private String description;

    @NotNull
    @Size(max = 1000)
    @Column(unique = true)
    private String artwork;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PlaylistLink> tracks;

    public Playlist() {
        super();
        tracks = Collections.emptyList();
    }
    
    public Playlist(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description, @NotNull @Size(max = 1000) String artwork) {
    	super();
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.artwork = artwork;
    	this.tracks = new ArrayList<>();
    }

    public Playlist(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description,
                    @NotNull @Size(max = 1000) String artwork, List<PlaylistLink> tracks) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.artwork = artwork;
        this.tracks = tracks;
    }

    public Playlist(long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public List<PlaylistLink> getTracks() {
        return tracks;
    }

    public void setTracks(List<PlaylistLink> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;

        Playlist playlist = (Playlist) o;
        
        if (!Objects.equals(name, playlist.name)) return false;
        if (!Objects.equals(description, playlist.description)) return false;
        if (!Objects.equals(artwork, playlist.artwork)) return false;
        return Objects.equals(tracks, playlist.tracks);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (artwork != null ? artwork.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", artwork='" + artwork + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
