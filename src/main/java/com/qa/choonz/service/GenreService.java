package com.qa.choonz.service;

import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private GenreRepository repo;
    private GenreMapper mapper;

    public GenreService(GenreRepository repo, GenreMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public GenreDTO create(Genre genre, UserDetails user) {
        // ensure only admin can create genre
        if (user == null || user.getRole() != UserRole.ADMIN){return null;}

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

    public GenreDTO update(Genre genre, long id, UserDetails user) {
        // ensure only admin can edit genre
        if (user == null || user.getRole() != UserRole.ADMIN){return null;}

        Genre toUpdate = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        toUpdate.setName(genre.getName());
        toUpdate.setDescription(genre.getDescription());
        Genre updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id, UserDetails user) {
        // ensure only admin can delete genre
        if (user == null || user.getRole() != UserRole.ADMIN){return false;}

        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
