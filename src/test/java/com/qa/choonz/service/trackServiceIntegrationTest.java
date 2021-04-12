package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class trackServiceIntegrationTest {

    @Autowired
    private TrackService trackService;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackMapper trackMapper;

    @SuppressWarnings("unused")
    private List<Track> tracks;
    private List<TrackDTO> trackDTOs;

    private Track validTrack;
    @SuppressWarnings("unused")
    private TrackDTO validTrackDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        validTrack = new Track();

        tracks = new ArrayList<>();
        trackDTOs = new ArrayList<>();

        trackRepository.deleteAll();

        validTrack = trackRepository.save(validTrack);

        validTrackDTO = trackMapper.mapToDeepDTO(validTrack);

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
        TrackDTO updatedDTO = trackMapper.mapToShallowDTO(updatedTrack);
        assertThat(updatedDTO).isEqualTo(trackService.update(updatedTrack, updatedTrack.getId(), user));
    }

    @Test
    public void deleteTrack() {

        assertThat(trackService.delete(validTrack.getId(), user)).isTrue();
    }

}