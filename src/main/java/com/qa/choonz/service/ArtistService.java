package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.ArtistMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

@Service
public class ArtistService {

    private ArtistRepository repo;
    private ArtistMapper mapper;

    public ArtistService(ArtistRepository repo, ArtistMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public ArtistDTO create(Artist artist) {
        Artist created = this.repo.save(artist);
        return mapper.mapToDeepDTO(created);
    }

    public List<ArtistDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public ArtistDTO read(long id) {
        Artist found = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public ArtistDTO update(Artist artist, long id) {
        Artist toUpdate = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
        toUpdate.setName(artist.getName());
        toUpdate.setAlbums(artist.getAlbums());
        Artist updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }
}
