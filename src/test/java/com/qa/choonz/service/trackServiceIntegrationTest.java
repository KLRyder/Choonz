package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;

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

	@BeforeEach
	public void init() {
		validTrack = new Track();

		tracks = new ArrayList<Track>();
		trackDTOs = new ArrayList<TrackDTO>();

		trackRepository.deleteAll();

		validTrack = trackRepository.save(validTrack);

		validTrackDTO = trackMapper.mapToDeepDTO(validTrack);
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
		assertThat(updatedDTO).isEqualTo(trackService.update(updatedTrack, updatedTrack.getId()));
	}

	@Test
	public void deleteTrack() {
		
		assertThat(trackService.delete(validTrack.getId())).isTrue();
	}

}