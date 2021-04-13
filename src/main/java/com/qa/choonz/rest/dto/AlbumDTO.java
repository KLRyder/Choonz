package com.qa.choonz.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AlbumDTO {

    private long id;
    private String name;
    private List<TrackDTO> tracks;
    private ArtistDTO artist;
    private List<GenreDTO> genres;
    private String cover;

    public AlbumDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AlbumDTO(long id, @NotNull @Size(max=100) String name, ArtistDTO artist, List<GenreDTO> genres, String cover) {
    	this.id = id;
        this.name = name;
        this.artist = artist;
        this.genres = genres;
        this.cover = cover;
        this.tracks = new ArrayList<TrackDTO>();
        
    }
    
    public AlbumDTO(long id, String name, List<TrackDTO> tracks, ArtistDTO artist, List<GenreDTO> genres, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artist = artist;
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

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
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
        if (!Objects.equals(artist, albumDTO.artist)) return false;
        if (!Objects.equals(genres, albumDTO.genres)) return false;
        return Objects.equals(cover, albumDTO.cover);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
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
                ", artist=" + artist +
                ", genre=" + genres +
                ", cover='" + cover + '\'' +
                '}';
    }

}
