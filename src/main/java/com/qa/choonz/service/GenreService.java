package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.rest.mapper.GenreMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;

@Service
public class GenreService {

    private GenreRepository repo;

    public GenreService(GenreRepository repo) {
        super();
        this.repo = repo;
    }


    public GenreDTO create(Genre genre) {
        Genre created = this.repo.save(genre);
        return GenreMapper.mapToDeepDTO(created);
    }

    public List<GenreDTO> read() {
        return this.repo.findAll().stream().map(GenreMapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public GenreDTO read(long id) {
        Genre found = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        return GenreMapper.mapToDeepDTO(found);
    }

    public GenreDTO update(Genre genre, long id) {
        Genre toUpdate = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        Genre updated = this.repo.save(toUpdate);
        return GenreMapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
