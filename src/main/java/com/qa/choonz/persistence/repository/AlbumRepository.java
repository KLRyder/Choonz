package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByNameContaining(String searchTerm);
}
