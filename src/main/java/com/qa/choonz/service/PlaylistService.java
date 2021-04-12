package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.PlaylistLink;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.PlaylistLinkRepository;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistService {

    private PlaylistRepository repo;
    private PlaylistLinkRepository linkRepo;
    private PlaylistMapper mapper;
    private ActiveSessions sessions;

    public PlaylistService(PlaylistRepository repo, PlaylistMapper mapper, PlaylistLinkRepository linkRepo, ActiveSessions sessions) {
        super();
        this.repo = repo;
        this.mapper = mapper;
        this.linkRepo = linkRepo;
        this.sessions = sessions;
    }

    public PlaylistDTO create(Playlist playlist) {
        sessions.testUsers();
        Playlist created = this.repo.save(playlist);
        return mapper.mapToDeepDTO(created);
    }

    public List<PlaylistDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public PlaylistDTO read(long id) {
        Playlist found = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public PlaylistDTO update(Playlist playlist, long id) {
        Playlist toUpdate = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        toUpdate.setName(playlist.getName());
        toUpdate.setDescription(playlist.getDescription());
        toUpdate.setArtwork(playlist.getArtwork());
        toUpdate.setTracks(playlist.getTracks());
        Playlist updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

    public boolean add(long id, long track) {
        var tempPl = new Playlist(id);
        var tempTrack = new Track(track);
        if (!linkRepo.existsByPlaylistAndTrack(tempPl, tempTrack)) {
            linkRepo.save(new PlaylistLink(tempPl, tempTrack));
            return true;
        }
        return false;
    }

    @Transactional
    public boolean remove(long id, long track) {
        var tempPl = new Playlist(id);
        var tempTrack = new Track(track);
        linkRepo.deleteByPlaylistAndTrack(tempPl, tempTrack);
        return !linkRepo.existsByPlaylistAndTrack(tempPl, tempTrack);
    }
}
