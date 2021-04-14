package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
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
public class trackServiceIntegrationTest {

    @Autowired
    private TrackService trackService;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackMapper trackMapper;

    private List<TrackDTO> trackDTOs;

    private Track validTrack;
    private TrackDTO validTrackDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        ArtistAlbumLink link = new ArtistAlbumLink();
        Artist validArtist = new Artist(1, "Artist name 1");
        Album validAlbum = new Album(1, "Album by artist 1", List.of(link), "Cover 1");
        Genre validGenre = new Genre(1, "Genre name 1", "Genre description 1");

        link.setArtist(validArtist);
        link.setAlbum(validAlbum);

        validTrack = new Track(1, "name1", validAlbum, 100, "lyrics1");
        validTrack.setGenre(validGenre);

        List<Track> tracks = new ArrayList<>();
        tracks.add(validTrack);
        validAlbum.setTracks(tracks);
        validGenre.setTracks(tracks);

        validTrackDTO = trackMapper.mapToDeepDTO(validTrack);

        trackDTOs = new ArrayList<>();
        trackDTOs.add(validTrackDTO);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllTracksTest() {

        List<TrackDTO> tracksInDB = trackService.read();

        assertThat(trackDTOs).isEqualTo(tracksInDB);
    }

    @Test
    public void readByIdTest() {
        assertThat(validTrackDTO).isEqualTo(trackService.read(validTrack.getId()));

    }

    @Test
    public void updateTrack() {
        Track updatedTrack = trackRepository.findAll().get(0);
        updatedTrack.setName("updated");
        TrackDTO updatedDTO = trackMapper.mapToDeepDTO(updatedTrack);
        assertThat(updatedDTO).isEqualTo(trackService.update(updatedTrack, updatedTrack.getId(), user));
    }

    @Test
    public void deleteTrack() {

        assertThat(trackService.delete(validTrack.getId(), user)).isTrue();
    }

}