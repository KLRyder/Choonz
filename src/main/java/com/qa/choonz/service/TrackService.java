package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.TrackMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;

@Service
public class TrackService {

    private TrackRepository repo;

    public TrackService(TrackRepository repo) {
        super();
        this.repo = repo;
    }

    public TrackDTO create(Track track) {
        Track created = this.repo.save(track);
        return TrackMapper.mapToDeepDTO(created);
    }

    public List<TrackDTO> read() {
        return this.repo.findAll().stream().map(TrackMapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public TrackDTO read(long id) {
        Track found = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
        return TrackMapper.mapToDeepDTO(found);
    }

    public TrackDTO update(Track track, long id) {
        Track toUpdate = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
        toUpdate.setName(track.getName());
        toUpdate.setAlbum(track.getAlbum());
        toUpdate.setDuration(track.getDuration());
        toUpdate.setLyrics(track.getLyrics());
        toUpdate.setPlaylist(track.getPlaylist());
        Track updated = this.repo.save(toUpdate);
        return TrackMapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
