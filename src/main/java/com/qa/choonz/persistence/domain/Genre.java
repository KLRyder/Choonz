package com.qa.choonz.persistence.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(max = 250)
    @Column(unique = true)
    private String description;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Album> albums;

    public Genre() {
        super();
        // TODO Auto-generated constructor stub
        albums = Collections.emptyList();
    }

    public Genre(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) 
    			 String description) {
    	super();
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.albums = new ArrayList<Album>();
    }
    
    public Genre(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
            List<Album> albums) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        if (!Objects.equals(name, genre.name)) return false;
        if (!Objects.equals(description, genre.description)) return false;
        return Objects.equals(albums, genre.albums);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", albums=" + albums +
                '}';
    }
}
