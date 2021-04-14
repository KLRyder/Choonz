package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;
import com.qa.choonz.service.GenreService;
import com.qa.choonz.utils.ActiveSessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class genreControllerUnitTest {
	
	@InjectMocks
	private GenreController genreController;
	
	@Mock
	private GenreService genreService;
	
	@Mock
	private GenreMapper genreMapper;

	@Mock
	private ActiveSessions sessions;
	
	private List<Genre> validGenres;
	private List<GenreDTO> validGenreDTOs;
	
	private Genre validGenre;
	private GenreDTO validGenreDTO;

	private UserDetails user = new UserDetails();
	
	@BeforeEach
	void init() {
		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");

		validGenre = new Genre(1, "genreName", "genreDescription");
		validGenreDTO = new GenreDTO(1, "genreName", "genreDescription");
		
		validGenres = new ArrayList<>();
		validGenreDTOs = new ArrayList<>();
		validGenres.add(validGenre);
		validGenreDTOs.add(validGenreDTO);
		
	}
	
	@Test
	void readAllGenres() {
		
		when(genreService.read()).thenReturn(validGenreDTOs);
		
		ResponseEntity<List<GenreDTO>> response =
				new ResponseEntity<>(validGenreDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(genreController.read());
		
		verify(genreService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(genreService.read(1L)).thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<>(validGenreDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(genreController.read(1L));
		
		verify(genreService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(genreService.create(Mockito.any(Genre.class), any(UserDetails.class))).thenReturn(validGenreDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<>(validGenreDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(genreController.create(validGenre,"user"));
		
		verify(genreService, times(1)).create(Mockito.any(Genre.class), any(UserDetails.class));
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void updateGenre() {
		
		when(genreService.update(validGenre, validGenre.getId(), user))
			 .thenReturn(validGenreDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<>(validGenreDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(genreController.update(validGenre, validGenre.getId(), "user"));
		
		verify(genreService, times(1)).update(validGenre, validGenre.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void deleteGenre() {
		
		when(genreService.delete(validGenre.getId(), user)).thenReturn(true);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<GenreDTO> response =
				new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(genreController.delete(validGenre.getId(), "user"));
		
		verify(genreService, times(1)).delete(validGenre.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}
	

}
