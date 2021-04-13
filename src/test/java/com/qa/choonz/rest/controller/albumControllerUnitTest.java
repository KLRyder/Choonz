package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
import com.qa.choonz.service.AlbumService;
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
public class albumControllerUnitTest {
	
	@InjectMocks
	private AlbumController albumController;
	
	@Mock
	private AlbumService albumService;
	
	@Mock
	private AlbumMapper albumMapper;

	@Mock
	private ActiveSessions sessions;
	
	private List<Album> validAlbums;
	private List<AlbumDTO> validAlbumDTOs;
	
	private Album validAlbum;
	private AlbumDTO validAlbumDTO;
	
	Artist artist = new Artist(1, "Rick");
	ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
	
	Genre genre = new Genre(1, "Name", "GenDescrip");
	GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
	private UserDetails user = new UserDetails();
	
	@BeforeEach
	void init() {
		
		Artist artist = new Artist(1, "Rick");
		ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
		
		Genre genre = new Genre(1, "Name", "GenDescrip");
		GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
		List<GenreDTO> genreDTOS = new ArrayList<>();
		genreDTOS.add(genreDTO);
		
		validAlbum = new Album(1, "Name", artist,"Cover");
		validAlbumDTO = new AlbumDTO(1, "Name", artistDTO, genreDTOS, "Cover");
		
		validAlbums = new ArrayList<>();
		validAlbumDTOs = new ArrayList<>();
		validAlbums.add(validAlbum);
		validAlbumDTOs.add(validAlbumDTO);

		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");
	}
	
	@Test
	void readAllAlbum() {
		
		when(albumService.read()).thenReturn(validAlbumDTOs);

		ResponseEntity<List<AlbumDTO>> response =
				new ResponseEntity<>(validAlbumDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(albumController.read());
		
		verify(albumService, times(1)).read();
	}
	
	@Test
	void readAlbumByID() {
		
		when(albumService.read(1L)).thenReturn(validAlbumDTO);

		ResponseEntity<AlbumDTO> response =
				new ResponseEntity<>(validAlbumDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(albumController.read(1L));
		
		verify(albumService, times(1)).read(1L);
		
	}
	
	@Test
	void createAlbum() {
		
		when(albumService.create(validAlbum, user)).thenReturn(validAlbumDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);

		ResponseEntity<AlbumDTO> response = 
				new ResponseEntity<>(validAlbumDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(albumController.create(validAlbum, "user"));
		
		verify(albumService, times(1)).create(validAlbum, user);
		verify(sessions, times(1)).getSession(any(String.class));
		
	}
	
	@Test
	void updateAlbum() {
		
		when(albumService.update(validAlbum, validAlbum.getId(), user))
			 .thenReturn(validAlbumDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);

		ResponseEntity<AlbumDTO> response =
				new ResponseEntity<>(validAlbumDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(albumController.update(validAlbum, validAlbum.getId(), "user"));
		
		verify(albumService, times(1)).update(validAlbum, validAlbum.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void deleteAlbum() {
		
		when(albumService.delete(validAlbum.getId(), user)).thenReturn(true);
		when(sessions.getSession(any(String.class))).thenReturn(user);

		ResponseEntity<AlbumDTO> response =
				new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(albumController.delete(validAlbum.getId(), "user"));
		
		verify(albumService, times(1)).delete(validAlbum.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}

}
