package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findAllByNameContaining(String searchTerm);
}
