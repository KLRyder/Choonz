package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;
import com.qa.choonz.service.ArtistService;
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
public class artistControllerUnitTest {
	
	@InjectMocks
	private ArtistController artistController;
	
	@Mock
	private ArtistService artistService;
	
	@Mock
	private ArtistMapper artistMapper;

	@Mock
	private ActiveSessions sessions;
	
	private List<Artist> validArtists;
	private List<ArtistDTO> validArtistDTOs;
	
	private Artist validArtist;
	private ArtistDTO validArtistDTO;
	private UserDetails user = new UserDetails();
	
	@BeforeEach
	void init() {
		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");

		validArtist = new Artist(1, "Rick");
		validArtistDTO = new ArtistDTO(1, "Rick");
		
		validArtists = new ArrayList<>();
		validArtistDTOs = new ArrayList<>();
		validArtists.add(validArtist);
		validArtistDTOs.add(validArtistDTO);
		
	}
	
	@Test
	void readAllGenres() {
		
		when(artistService.read()).thenReturn(validArtistDTOs);
		
		ResponseEntity<List<ArtistDTO>> response =
				new ResponseEntity<>(validArtistDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(artistController.read());
		
		verify(artistService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(artistService.read(1L)).thenReturn(validArtistDTO);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<>(validArtistDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(artistController.read(1L));
		
		verify(artistService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(artistService.create(validArtist, user)).thenReturn(validArtistDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<>(validArtistDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(artistController.create(validArtist, "user"));
		
		verify(artistService, times(1)).create(validArtist, user);
		verify(sessions, times(1)).getSession(any(String.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(artistService.update(validArtist, validArtist.getId(), user))
			 .thenReturn(validArtistDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<>(validArtistDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(artistController.update(validArtist, validArtist.getId(), "user"));
		
		verify(artistService, times(1)).update(validArtist, validArtist.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void deleteGenre() {
		
		when(artistService.delete(validArtist.getId(), user)).thenReturn(true);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<ArtistDTO> response =
				new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(artistController.delete(validArtist.getId(), "user"));
		
		verify(artistService, times(1)).delete(validArtist.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}

}
