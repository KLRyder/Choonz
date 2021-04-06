package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.PlaylistMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;

@Service
public class PlaylistService {

    private PlaylistRepository repo;
    private PlaylistMapper mapper;

    public PlaylistService(PlaylistRepository repo, PlaylistMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public PlaylistDTO create(Playlist playlist) {
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
        toUpdate.setName(toUpdate.getName());
        toUpdate.setDescription(toUpdate.getDescription());
        toUpdate.setArtwork(toUpdate.getArtwork());
        toUpdate.setTracks(toUpdate.getTracks());
        Playlist updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
