package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class ArtistDTO {

    private long id;
    private String name;
    private List<AlbumDTO> albums;
    private List<TrackDTO> tracks;

    public ArtistDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ArtistDTO(long id, String name, List<AlbumDTO> albums) {
        super();
        this.id = id;
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistDTO)) return false;

        ArtistDTO artistDTO = (ArtistDTO) o;

        if (id != artistDTO.id) return false;
        if (!Objects.equals(name, artistDTO.name)) return false;
        if (!Objects.equals(albums, artistDTO.albums)) return false;
        return Objects.equals(tracks, artistDTO.tracks);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ArtistDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", albums=" + albums +
                ", tracks=" + tracks +
                '}';
    }

}
