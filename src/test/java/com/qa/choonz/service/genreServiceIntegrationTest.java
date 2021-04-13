package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class genreServiceIntegrationTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    @SuppressWarnings("unused")
    private List<Genre> genres;
    private List<GenreDTO> genreDTOs;

    private Genre validGenre;
    @SuppressWarnings("unused")
    private GenreDTO validGenreDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        validGenre = new Genre();

        genres = new ArrayList<>();
        genreDTOs = new ArrayList<>();

        genreRepository.deleteAll();

        validGenre = genreRepository.save(validGenre);

        validGenreDTO = genreMapper.mapToDeepDTO(validGenre);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllGenresTest() {

        List<GenreDTO> genresInDB = genreService.read();

        assertThat(genreDTOs).isEqualTo(genresInDB);
    }

    @Test
    public void readByIdTest() {

        assertThat(validGenreDTO).isEqualTo(genreService.read(validGenre.getId()));

    }

    @Test
    public void updateGenre() {

        Genre updatedGenre = genreRepository.findAll().get(0);
        updatedGenre.setName("updated");
        GenreDTO updatedDTO = genreMapper.mapToShallowDTO(updatedGenre);
        assertThat(updatedDTO).isEqualTo(genreService.update(updatedGenre, updatedGenre.getId(), user));
    }

    @Test
    public void deleteGenre() {

        assertThat(genreService.delete(validGenre.getId(), user)).isTrue();
    }

}