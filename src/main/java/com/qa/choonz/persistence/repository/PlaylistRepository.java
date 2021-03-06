package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByNameContaining (String term);

    List<Playlist> findAllByCreator (UserDetails id);
}
