package com.qa.choonz.service;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrackService {

    private TrackRepository repo;
    private TrackMapper mapper;

    public TrackService(TrackRepository repo, TrackMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public TrackDTO create(Track track, UserDetails user) {
        // ensure only admin can create tracks
        if (user == null || user.getRole() != UserRole.ADMIN) {
            return null;
        }
        Track created = this.repo.save(track);
        return mapper.mapToDeepDTO(created);
    }

    public List<TrackDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public TrackDTO read(long id) {
        Track found = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public TrackDTO update(Track track, long id, UserDetails user) {
        // ensure only admin can edit tracks
        if (user == null || user.getRole() != UserRole.ADMIN) {
            return null;
        }

        Track toUpdate = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
        toUpdate.setName(track.getName());
        toUpdate.setAlbum(track.getAlbum());
        toUpdate.setDuration(track.getDuration());
        toUpdate.setLyrics(track.getLyrics());
        this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(this.repo.findById(id).orElseThrow(TrackNotFoundException::new));
    }

    public boolean delete(long id, UserDetails user) {
        // ensure only admin can delete tracks
        if (user == null || user.getRole() != UserRole.ADMIN) {
            return false;
        }
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

    public Set<Track> read(String term) {
        return Set.copyOf(repo.findAllByNameContaining(term));
    }
}
