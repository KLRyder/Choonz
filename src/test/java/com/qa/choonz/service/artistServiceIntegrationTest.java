package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class artistServiceIntegrationTest {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistMapper artistMapper;

    @SuppressWarnings("unused")
    private List<Artist> artists;
    private List<ArtistDTO> artistDTOs;

    private Artist validArtist;
    @SuppressWarnings("unused")
    private ArtistDTO validArtistDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        validArtist = new Artist();

        artists = new ArrayList<>();
        artistDTOs = new ArrayList<>();

        artistRepository.deleteAll();

        validArtist = artistRepository.save(validArtist);

        validArtistDTO = artistMapper.mapToDeepDTO(validArtist);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllArtistsTest() {

        List<ArtistDTO> artistsInDB = artistService.read();

        assertThat(artistDTOs).isEqualTo(artistsInDB);
    }

    @Test
    public void readByIdTest() {

        assertThat(validArtistDTO).isEqualTo(artistService.read(validArtist.getId()));

    }

    @Test
    public void updateArtist() {

        Artist updatedArtist = artistRepository.findAll().get(0);
        updatedArtist.setName("updated");
        ArtistDTO updatedDTO = artistMapper.mapToShallowDTO(updatedArtist);
        assertThat(updatedDTO).isEqualTo(artistService.update(updatedArtist, updatedArtist.getId(), user));
    }

    @Test
    public void deleteArtist() {

        assertThat(artistService.delete(validArtist.getId(), user)).isTrue();
    }

}