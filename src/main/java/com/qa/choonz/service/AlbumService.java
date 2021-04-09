package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;

@Service
public class AlbumService {

    private AlbumRepository repo;
    private AlbumMapper mapper;

    public AlbumService(AlbumRepository repo, AlbumMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public AlbumDTO create(Album album) {
        Album created = this.repo.save(album);
        return mapper.mapToDeepDTO(created);
    }

    public List<AlbumDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public AlbumDTO read(long id) {
        Album found = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public List<AlbumDTO> readByAlbum(long id) {
        return this.repo.findAllByArtist_Id(id).stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public AlbumDTO update(Album album, long id) {
        Album toUpdate = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        toUpdate.setName(album.getName());
        toUpdate.setArtist(album.getArtist());
        toUpdate.setCover(album.getCover());
        Album updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }
}
