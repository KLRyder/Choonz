package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.PlaylistLink;
import com.qa.choonz.persistence.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistLinkRepository extends JpaRepository<PlaylistLink, Long> {
    boolean existsByPlaylistAndTrack(Playlist playlist, Track track);
    void deleteByPlaylistAndTrack(Playlist playlistId, Track trackId);
}
