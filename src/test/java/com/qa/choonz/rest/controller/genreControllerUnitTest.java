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

import com.google.common.base.Optional;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;
import com.qa.choonz.service.GenreService;

@ExtendWith({MockitoExtension.class})
public class genreControllerUnitTest {
	
	@InjectMocks
	private GenreController genreController;
	
	@Mock
	private GenreService genreService;
	
	@Mock
	private GenreMapper genreMapper;
	
	private List<Genre> validGenres;
	private List<GenreDTO> validGenreDTOs;
	
	private Genre validGenre;
	private GenreDTO validGenreDTO;
	
	@BeforeEach
	void init() {
		validGenre = new Genre(1, "genreName", "genreDescription");
		validGenreDTO = new GenreDTO(1, "genreName", "genreDescription");
		
		validGenres = new ArrayList<Genre>();
		validGenreDTOs = new ArrayList<GenreDTO>();
		validGenres.add(validGenre);
		validGenreDTOs.add(validGenreDTO);
		
	}
	
	@Test
	void readAllGenres() {
		
		when(genreService.read()).thenReturn(validGenreDTOs);
		
		ResponseEntity<List<GenreDTO>> response = 
				new ResponseEntity<List<GenreDTO>>(validGenreDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(genreController.read());
		
		verify(genreService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(genreService.read(1L)).thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(genreController.read(1L));
		
		verify(genreService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(genreService.create(Mockito.any(Genre.class))).thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response = 
				new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(genreController.create(validGenre));
		
		verify(genreService, times(1)).create(Mockito.any(Genre.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(genreService.update(validGenre, validGenre.getId()))
			 .thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(genreController.update(validGenre, validGenre.getId()));
		
		verify(genreService, times(1)).update(validGenre, validGenre.getId());
	}
	
	@Test
	void deleteGenre() {
		
		when(genreService.delete(validGenre.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = 
				new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(genreController.delete(validGenre.getId()));
		
		verify(genreService, times(1)).delete(validGenre.getId());
	}
	

}
