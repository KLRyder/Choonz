package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;
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
public class artistServiceIntegrationTest {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistMapper artistMapper;

    private List<Artist> artists;
    private List<ArtistDTO> artistDTOs;

    private Artist validArtist;
    private ArtistDTO validArtistDTO;

    private UserDetails user;
    private Album validAlbum;
    private Genre validGenre;
    private ArtistAlbumLink validLink;

    @BeforeEach
    public void init() {
        validArtist = new Artist();

        artists = new ArrayList<>();
        artistDTOs = new ArrayList<>();

        validLink = new ArtistAlbumLink(1);
        validArtist = new Artist(1, "Artist name 1");
        validAlbum = new Album(1, "Album by artist 1", List.of(validLink), "Cover 1");
        validGenre = new Genre(1, "Genre name 1", "Genre description 1");
        validArtist.setAlbums(List.of(validLink));
        validLink.setAlbum(validAlbum);
        validLink.setArtist(validArtist);

        Track validTrack = new Track(1, "name1", validAlbum, 100, "lyrics1");
        validTrack.setGenre(validGenre);

        List<Track> tracks = new ArrayList<>();
        tracks.add(validTrack);
        validAlbum.setTracks(tracks);
        validGenre.setTracks(tracks);
        validArtist.setAlbums(List.of(validLink));

        validArtistDTO = artistMapper.mapToDeepDTO(validArtist);
        artists.add(validArtist);
        artistDTOs.add(validArtistDTO);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllArtistsTest() {
        assertThat(artistDTOs).isEqualTo(artistService.read());
    }

    @Test
    public void readByIdTest() {
        assertThat(artistService.read(validArtist.getId())).isEqualTo(validArtistDTO);
    }

    @Test
    public void updateArtist() {

        Artist updatedArtist = artistRepository.findAll().get(0);
        updatedArtist.setName("updated");
        ArtistDTO updatedDTO = artistMapper.mapToDeepDTO(updatedArtist);
        assertThat(artistService.update(updatedArtist, updatedArtist.getId(), user)).isEqualTo(updatedDTO);
    }

    @Test
    public void deleteArtist() {

        assertThat(artistService.delete(validArtist.getId(), user)).isTrue();
    }

}