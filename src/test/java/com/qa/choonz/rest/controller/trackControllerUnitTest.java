package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
import com.qa.choonz.service.TrackService;

@ExtendWith({MockitoExtension.class})
public class trackControllerUnitTest {
	
	@InjectMocks
	private TrackController trackController;
	
	@Mock
	private TrackService trackService;
	
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
	
	@BeforeEach
	void init() {	
		Album album = new Album();
		Playlist playlist = new Playlist();
		Artist artist = new Artist();
		Genre genre = new Genre();
		
		validTrack = new Track(1L, "Rick", album, playlist, 300, "RickandRoll");
		validTrackDTO = new TrackDTO();
		
		validTracks = new ArrayList<Track>();
		validTrackDTOs = new ArrayList<TrackDTO>();
		validTracks.add(validTrack);
		validTrackDTOs.add(validTrackDTO);
	}
	
	@Test
	void readAllGenres() {
		
		when(trackService.read()).thenReturn(validTrackDTOs);
		
		ResponseEntity<List<TrackDTO>> response = 
				new ResponseEntity<List<TrackDTO>>(validTrackDTOs, HttpStatus.OK);
		
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
		
		when(trackService.create(Mockito.any(Track.class))).thenReturn(validTrackDTO);
		
		ResponseEntity<TrackDTO> response = 
				new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(trackController.create(validTrack));
		
		verify(trackService, times(1)).create(Mockito.any(Track.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(trackService.update(validTrack, validTrack.getId()))
			 .thenReturn(validTrackDTO);
		
		ResponseEntity<TrackDTO> response =
				new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(trackController.update(validTrack, validTrack.getId()));
		
		verify(trackService, times(1)).update(validTrack, validTrack.getId());
	}
	
	@Test
	void deleteGenre() {
		
		when(trackService.delete(validTrack.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = 
				new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(trackController.delete(validTrack.getId()));
		
		verify(trackService, times(1)).delete(validTrack.getId());
	}

}
