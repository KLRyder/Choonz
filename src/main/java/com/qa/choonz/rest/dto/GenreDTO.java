package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Track;

public class GenreDTO {

    private long id;
    private String name;
    private String description;
    private List<Album> albums;
    private List<Track> tracks;

    public GenreDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public GenreDTO(long id, String name, String description, List<Album> albums) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.albums = albums;
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

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", albums=" + albums +
                ", tracks=" + tracks +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(albums, description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenreDTO)) {
            return false;
        }
        GenreDTO other = (GenreDTO) obj;
        return Objects.equals(albums, other.albums) && Objects.equals(description, other.description) && id == other.id
                && Objects.equals(name, other.name);
    }
}
