package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
import com.qa.choonz.service.TrackService;
import com.qa.choonz.utils.ActiveSessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class trackControllerUnitTest {
	
	@InjectMocks
	private TrackController trackController;
	
	@Mock
	private TrackService trackService;

	@Mock
	private ActiveSessions sessions;

	@Mock
	private TrackMapper trackMapper;
	
	private List<Track> validTracks;
	private List<TrackDTO> validTrackDTOs;
	
	private Track validTrack;
	private TrackDTO validTrackDTO;
	private UserDetails user = new UserDetails();
	
	Genre genre = new Genre(1L, "genreName", "genreDescrip");
	Album album = new Album();
	Playlist playlist = new Playlist();
	Artist artist = new Artist();
	
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
	void readAllGenres() {
		
		when(trackService.read()).thenReturn(validTrackDTOs);
		
		ResponseEntity<List<TrackDTO>> response = 
				new ResponseEntity<>(validTrackDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(trackController.read());
		
		verify(trackService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(trackService.read(1L)).thenReturn(validTrackDTO);
		
		ResponseEntity<TrackDTO> response =
				new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(trackController.read(1L));
		
		verify(trackService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(trackService.create(any(Track.class), any(UserDetails.class))).thenReturn(validTrackDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<TrackDTO> response = 
				new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(trackController.create(validTrack, "sessID"));
		
		verify(trackService, times(1)).create(any(Track.class), any(UserDetails.class));
		verify(sessions, times(1)).getSession(any(String.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(trackService.update(validTrack, validTrack.getId(), any(UserDetails.class)))
			 .thenReturn(validTrackDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<TrackDTO> response =
				new ResponseEntity<>(validTrackDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(trackController.update(validTrack, validTrack.getId(), "sessID"));
		
		verify(trackService, times(1)).update(validTrack, validTrack.getId(), any(UserDetails.class));
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void deleteGenre() {
		
		when(trackService.delete(validTrack.getId(), user)).thenReturn(true);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<TrackDTO> response =
				new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(trackController.delete(validTrack.getId(), "sessID"));
		
		verify(trackService, times(1)).delete(validTrack.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}

}
