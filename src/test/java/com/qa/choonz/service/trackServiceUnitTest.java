package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;

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
		
		assertThat(validTrackDTO).isEqualTo(trackService.create(validTrack));
		
		verify(trackRepo, times(1)).save(Mockito.any(Track.class));
		verify(trackMapper, times(1)).mapToDeepDTO(Mockito.any(Track.class));
		
	}
	
	@Test
	void updateTrack() {
		Track updateTrack = new Track(1, "Ricky", album, playlist, 300, "RickyandRoll");
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