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
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Track> tracks;

    public Genre() {
        super();
        tracks = Collections.emptyList();
    }

    public Genre(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) 
    			 String description) {
    	super();
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.tracks = new ArrayList<>();
    }
    
    public Genre(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
            List<Track> tracks) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.tracks = tracks;
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        if (!Objects.equals(getName(), genre.getName())) return false;
        if (!Objects.equals(getDescription(), genre.getDescription())) return false;
        return Objects.equals(getTracks(), genre.getTracks());
    }

    @Override
    public int hashCode() {
		int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", albums=" + tracks +
                '}';
    }
}
