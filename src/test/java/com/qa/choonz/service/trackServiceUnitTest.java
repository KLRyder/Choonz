package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class trackServiceUnitTest {

	@InjectMocks
	private TrackService trackService;

	@Mock
	private TrackRepository trackRepo;

	@Mock
	private TrackMapper trackMapper;

	private List<Track> validTracks;
	private List<TrackDTO> validTrackDTOs;

	private Track validTrack;
	private TrackDTO validTrackDTO;

	Genre genre = new Genre(1L, "genreName", "genreDescrip");
	Album album = new Album();
	Playlist playlist = new Playlist();
	Artist artist = new Artist();
	private UserDetails user = new UserDetails();
	
	
	@BeforeEach
	void init() {
		Album album = new Album();
		Playlist playlist = new Playlist();
		Artist artist = new Artist();
		Genre genre = new Genre();
		
		validTrack = new Track(1L, "Rick", album, 300, "RickandRoll");
		validTrackDTO = new TrackDTO();
		
		validTracks = new ArrayList<>();
		validTrackDTOs = new ArrayList<>();
		validTracks.add(validTrack);
		validTrackDTOs.add(validTrackDTO);

		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");
	}
	
	@Test
	void readTrackByID() {
		
		when(trackRepo.findById(1L)).thenReturn(Optional.of(validTrack));
		when(trackMapper.mapToDeepDTO(Mockito.any(Track.class))).thenReturn(validTrackDTO);
		
		assertThat(validTrackDTO).isEqualTo(trackService.read(1L));
		
		verify(trackRepo, times(1)).findById(1L);
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void readAllTrack() {
		
		when(trackRepo.findAll()).thenReturn(validTracks);
		when(trackMapper.mapToDeepDTO(Mockito.any(Track.class))).thenReturn(validTrackDTO);
		
		assertThat(validTrackDTOs).isEqualTo(trackService.read());
		
		verify(trackRepo, times(1)).findAll();
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void createTrack() {
		
		when(trackRepo.save(Mockito.any(Track.class))).thenReturn(validTrack);
		when(trackMapper.mapToDeepDTO(Mockito.any(Track.class)))
			.thenReturn(validTrackDTO);
		
		assertThat(validTrackDTO).isEqualTo(trackService.create(validTrack, user));
		
		verify(trackRepo, times(1)).save(Mockito.any(Track.class));
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void updateTrack() {
		Track updateTrack = new Track(1, "Ricky", album, 300, "RickyandRoll");
		TrackDTO updateTrackDTO = new TrackDTO();
		
		when(trackRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(validTrack));
		when(trackRepo.save(Mockito.any(Track.class))).thenReturn(updateTrack);
		when(trackMapper.mapToDeepDTO(Mockito.any(Track.class))).thenReturn(updateTrackDTO);
		
		TrackDTO testTrackDTO = trackService.update(updateTrack, validTrack.getId(), user);
		
		assertThat(updateTrackDTO).isEqualTo(testTrackDTO);
		
		verify(trackRepo, times(2)).findById(Mockito.any(Long.class));
		verify(trackRepo, times(1)).save(Mockito.any(Track.class));
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void deleteTrack() {
		
		when(trackRepo.existsById(Mockito.any(Long.class)))
			.thenReturn(true, false);
		
		assertThat(false).isEqualTo(trackService.delete(validTrack.getId(), user));
		
		verify(trackRepo, times(1)).existsById(Mockito.any(Long.class));
		
	}
}