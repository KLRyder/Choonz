package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.GenreMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;

@Service
public class GenreService {

    private GenreRepository repo;
    private GenreMapper mapper;

    public GenreService(GenreRepository repo, GenreMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public GenreDTO create(Genre genre) {
        Genre created = this.repo.save(genre);
        return mapper.mapToDeepDTO(created);
    }

    public List<GenreDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public GenreDTO read(long id) {
        Genre found = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public GenreDTO update(Genre genre, long id) {
        Genre toUpdate = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        Genre updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
