package com.qa.choonz.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumDTO {

    private long id;
    private String name;
    private List<TrackDTO> tracks;
    private List<ArtistDTO> artists;
    private List<GenreDTO> genres;
    private String cover;

    public AlbumDTO() {
        super();
        // TODO Auto-generated constructor stub
        tracks = new ArrayList<>();
        artists = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public AlbumDTO(long id, @NotNull @Size(max=100) String name, List<ArtistDTO> artists, List<GenreDTO> genres, String cover) {
    	this.id = id;
        this.name = name;
        this.artists = artists;
        this.genres = genres;
        this.cover = cover;
        this.tracks = new ArrayList<>();
        
    }
    
    public AlbumDTO(long id, String name, List<TrackDTO> tracks, List<ArtistDTO> artists, List<GenreDTO> genres, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artists = artists;
        this.genres = genres;
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

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<ArtistDTO> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistDTO> artists) {
        this.artists = artists;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
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
        if (!(o instanceof AlbumDTO)) return false;

        AlbumDTO albumDTO = (AlbumDTO) o;

        if (id != albumDTO.id) return false;
        if (!Objects.equals(name, albumDTO.name)) return false;
        if (!Objects.equals(tracks, albumDTO.tracks)) return false;
        if (!Objects.equals(artists, albumDTO.artists)) return false;
        if (!Objects.equals(genres, albumDTO.genres)) return false;
        return Objects.equals(cover, albumDTO.cover);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (artists != null ? artists.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tracks=" + tracks +
                ", artist=" + artists +
                ", genre=" + genres +
                ", cover='" + cover + '\'' +
                '}';
    }

}
