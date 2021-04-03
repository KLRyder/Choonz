package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.AlbumMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;

@Service
public class AlbumService {

    private AlbumRepository repo;

    public AlbumService(AlbumRepository repo) {
        super();
        this.repo = repo;
    }

    public AlbumDTO create(Album album) {
        Album created = this.repo.save(album);
        return AlbumMapper.mapToDeepDTO(created);
    }

    public List<AlbumDTO> read() {
        return this.repo.findAll().stream().map(AlbumMapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public AlbumDTO read(long id) {
        Album found = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        return AlbumMapper.mapToDeepDTO(found);
    }

    public AlbumDTO update(Album album, long id) {
        Album toUpdate = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        toUpdate.setName(toUpdate.getName());
        toUpdate.setTracks(toUpdate.getTracks());
        toUpdate.setArtist(toUpdate.getArtist());
        toUpdate.setCover(toUpdate.getCover());
        Album updated = this.repo.save(toUpdate);
        return AlbumMapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
