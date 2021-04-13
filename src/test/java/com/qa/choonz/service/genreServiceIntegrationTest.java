package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class genreServiceIntegrationTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    private Genre validGenre;
    private GenreDTO validGenreDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        Artist validArtist = new Artist(1, "Artist name 1");
        Album validAlbum = new Album(1, "Album by artist 1", validArtist, "Cover 1");
        validGenre = new Genre(1, "Genre name 1", "Genre description 1");

        Track validTrack = new Track(1, "name1", validAlbum, 100, "lyrics1");
        validTrack.setGenre(validGenre);

        List<Track> tracks = new ArrayList<>();
        tracks.add(validTrack);
        validAlbum.setTracks(tracks);
        validGenre.setTracks(tracks);

        validGenreDTO = genreMapper.mapToDeepDTO(validGenre);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllGenresTest() {
        assertThat(genreService.read()).isEqualTo(List.of(validGenreDTO));
    }

    @Test
    public void readByIdTest() {
        assertThat(genreService.read(validGenre.getId())).isEqualTo(validGenreDTO);
    }

    @Test
    public void updateGenre() {
        Genre updatedGenre = genreRepository.findAll().get(0);
        updatedGenre.setName("updated");
        GenreDTO updatedDTO = genreMapper.mapToDeepDTO(updatedGenre);
        assertThat(genreService.update(updatedGenre, updatedGenre.getId(), user)).isEqualTo(updatedDTO);
    }

    @Test
    public void deleteGenre() {

        assertThat(genreService.delete(validGenre.getId(), user)).isTrue();
    }

}