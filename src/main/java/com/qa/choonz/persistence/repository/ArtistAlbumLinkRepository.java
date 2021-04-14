package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.ArtistAlbumLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistAlbumLinkRepository extends JpaRepository<ArtistAlbumLink, Long> {
    boolean existsByArtistAndAlbum(Artist tempArtist, Album tempAlbum);

    void deleteByArtistAndAlbum(Artist tempArtist, Album tempAlbum);
}
