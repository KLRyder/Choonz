package com.qa.choonz.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenreDTO {

    private long id;
    private String name;
    private String description;
    private List<AlbumDTO> albums;
    private List<TrackDTO> tracks;

    public GenreDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

   public GenreDTO(long id, String name, String description) {
	   super();
	   this.id = id;
	   this.name = name;
	   this.description = description;
	   this.albums = new ArrayList<>();
	   this.tracks = new ArrayList<>();
   }
    
    public GenreDTO(long id, String name, String description, List<AlbumDTO> albums) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.albums = albums;
        this.tracks = new ArrayList<>();
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

    public List<AlbumDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDTO> albums) {
        this.albums = albums;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreDTO)) return false;

        GenreDTO genreDTO = (GenreDTO) o;

        if (id != genreDTO.id) return false;
        if (!Objects.equals(name, genreDTO.name)) return false;
        if (!Objects.equals(description, genreDTO.description)) return false;
        if (!Objects.equals(albums, genreDTO.albums)) return false;
        return Objects.equals(tracks, genreDTO.tracks);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }
}
