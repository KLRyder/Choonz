package com.qa.choonz.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlaylistDTO {

    private long id;
    private String name;
    private String description;
    private String artwork;
    private List<TrackDTO> tracks;

    public PlaylistDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public PlaylistDTO(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description, @NotNull @Size(max = 1000) String artwork) {
    	super();
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.artwork = artwork;
    	this.tracks = new ArrayList<>();
    	
    }

    public PlaylistDTO(long id, String name, String description, String artwork, List<TrackDTO> tracks) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.artwork = artwork;
        this.tracks = tracks;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the artwork
     */
    public String getArtwork() {
        return artwork;
    }

    /**
     * @param artwork the artwork to set
     */
    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    /**
     * @return the tracks
     */
    public List<TrackDTO> getTracks() {
        return tracks;
    }

    /**
     * @param tracks the tracks to set
     */
    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistDTO)) return false;

        PlaylistDTO that = (PlaylistDTO) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(artwork, that.artwork)) return false;
        return Objects.equals(tracks, that.tracks);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (artwork != null ? artwork.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", artwork='" + artwork + '\'' +
                ", tracks=" + tracks +
                '}';
    }

}
