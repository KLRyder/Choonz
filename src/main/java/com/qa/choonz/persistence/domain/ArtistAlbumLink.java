package com.qa.choonz.persistence.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ArtistAlbumLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(targetEntity = Artist.class, fetch = FetchType.LAZY)
    Artist artist;

    @ManyToOne(targetEntity = Album.class, fetch = FetchType.LAZY)
    Album album;

    public ArtistAlbumLink(long id) {
        this.id = id;
    }

    public ArtistAlbumLink() {
    }

    public ArtistAlbumLink(Artist artist, Album album) {
        this.album = album;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistAlbumLink)) return false;

        ArtistAlbumLink that = (ArtistAlbumLink) o;

        if (!Objects.equals(artist, that.artist)) return false;
        return Objects.equals(album, that.album);
    }

    @Override
    public int hashCode() {
        int result = artist != null ? artist.hashCode() : 0;
        result = 31 * result + (album != null ? album.hashCode() : 0);
        return result;
    }
}
