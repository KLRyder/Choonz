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

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;
import com.qa.choonz.service.ArtistService;

@ExtendWith({MockitoExtension.class})
public class artistControllerUnitTest {
	
	@InjectMocks
	private ArtistController artistController;
	
	@Mock
	private ArtistService artistService;
	
	@Mock
	private ArtistMapper artistMapper;
	
	private List<Artist> validArtists;
	private List<ArtistDTO> validArtistDTOs;
	
	private Artist validArtist;
	private ArtistDTO validArtistDTO;
	
	@BeforeEach
	void init() {
		validArtist = new Artist(1, "Rick", "Password");
		validArtistDTO = new ArtistDTO(1, "Rick");
		
		validArtists = new ArrayList<Artist>();
		validArtistDTOs = new ArrayList<ArtistDTO>();
		validArtists.add(validArtist);
		validArtistDTOs.add(validArtistDTO);
		
	}
	
	@Test
	void readAllGenres() {
		
		when(artistService.read()).thenReturn(validArtistDTOs);
		
		ResponseEntity<List<ArtistDTO>> response = 
				new ResponseEntity<List<ArtistDTO>>(validArtistDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(artistController.read());
		
		verify(artistService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(artistService.read(1L)).thenReturn(validArtistDTO);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(artistController.read(1L));
		
		verify(artistService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(artistService.create(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		ResponseEntity<ArtistDTO> response = 
				new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(artistController.create(validArtist));
		
		verify(artistService, times(1)).create(Mockito.any(Artist.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(artistService.update(validArtist, validArtist.getId()))
			 .thenReturn(validArtistDTO);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(artistController.update(validArtist, validArtist.getId()));
		
		verify(artistService, times(1)).update(validArtist, validArtist.getId());
	}
	
	@Test
	void deleteGenre() {
		
		when(artistService.delete(validArtist.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = 
				new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(artistController.delete(validArtist.getId()));
		
		verify(artistService, times(1)).delete(validArtist.getId());
	}

}
