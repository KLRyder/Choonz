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
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Album> albums;

    public Artist() {
        super();
        albums = Collections.emptyList();
    }

   public Artist(long id, @NotNull @Size(max = 100) String name) {
	   super();
	   this.id = id;
	   this.name = name;
	   this.albums = new ArrayList<>();
   }
    
    public Artist(long id, @NotNull @Size(max = 100) String name, List<Album> albums) {
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
    
    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        if (!Objects.equals(getName(), artist.getName())) return false;
        return Objects.equals(albums, artist.albums);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", albums=" + albums +
                '}';
    }
}
