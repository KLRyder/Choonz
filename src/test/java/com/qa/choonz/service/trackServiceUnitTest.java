package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;

@SpringBootTest
public class trackServiceUnitTest {

	@Autowired
	private TrackService trackService;

	@MockBean
	private TrackRepository trackRepo;

	@MockBean
	private TrackMapper trackMapper;

	private List<Track> validTracks;
	private List<TrackDTO> validTrackDTOs;

	private Track validTrack;
	private TrackDTO validTrackDTO;

	@BeforeEach
	void init() {
		validTrack = new Track(1, "Rick", null, null, 300, "RickandRoll");
		validTrackDTO = new TrackDTO();
		
		validTracks = new ArrayList<Track>();
		validTrackDTOs = new ArrayList<TrackDTO>();
		validTracks.add(validTrack);
		validTrackDTOs.add(validTrackDTO);
		
	}
	
	@Test
	void readTrackByID() {
		
	}
	
	@Test
	void readAllTrack() {
		
	}
	
	@Test
	void createTrack() {
		
		when(trackRepo.save(Mockito.any(Track.class))).thenReturn(validTrack);
		when (TrackMapper.mapToDeepDTO(Mockito.any(Track.class)))
			.thenReturn(validTrackDTO);
		
		verify(trackRepo, times(1)).save(Mockito.any(Track.class));
		verify(trackMapper, times(1));
		TrackMapper.mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void updateTrack() {
		Track updateTrack = new Track(1, "Ricky", null, null, 300, "RickyandRoll");
		TrackDTO updateTrackDTO = new TrackDTO();
		
		when(trackRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(validTrack));
		when(trackRepo.save(Mockito.any(Track.class))).thenReturn(updateTrack);
		when(trackMapper.mapToDeepDTO(Mockito.any(Track.class))).thenReturn(updateTrackDTO);
		
		TrackDTO testTrackDTO = trackService.update(updateTrack, validTrack.getId());
		
		assertThat(updateTrackDTO).isEqualTo(testTrackDTO);
		
		verify(trackRepo, times(1)).findById(Mockito.any(Long.class));
		verify(trackRepo, times(1)).save(Mockito.any(Track.class));
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void deleteTrack() {
		
		when(trackRepo.existsById(Mockito.any(Long.class)))
			.thenReturn(true, false);
		
		assertThat(false).isEqualTo(trackService.delete(validTrack.getId()));
		
		verify(trackRepo, times(1)).existsById(Mockito.any(Long.class));
		
	}
}