package com.qa.choonz.rest.controller;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import com.qa.choonz.service.PlaylistService;
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
public class playlistControllerUnitTest {
	
	@InjectMocks
	private PlaylistController playlistController;
	
	@Mock
	private PlaylistService playlistService;
	
	@Mock
	private PlaylistMapper playlistMapper;

	@Mock
	private ActiveSessions sessions;

	private UserDetails user = new UserDetails();

	
	private List<Playlist> validPlaylists;
	private List<PlaylistDTO> validPlaylistDTOs;
	
	private Playlist validPlaylist;
	private PlaylistDTO validPlaylistDTO;
	
	
	@BeforeEach
	void init() {
		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");

		validPlaylist = new Playlist(1, "Name" ,"PlaylistDescrip", "Artwork", user);
		validPlaylistDTO = new PlaylistDTO(1, "Name" ,"PlaylistDescrip", "Artwork");
		
		validPlaylists = new ArrayList<>();
		validPlaylistDTOs = new ArrayList<>();
		validPlaylists.add(validPlaylist);
		validPlaylistDTOs.add(validPlaylistDTO);
	}
	
	@Test
	void readAllGenres() {
		
		when(playlistService.read()).thenReturn(validPlaylistDTOs);
		
		ResponseEntity<List<PlaylistDTO>> response = 
				new ResponseEntity<>(validPlaylistDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(playlistController.read());
		
		verify(playlistService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(playlistService.read(1L)).thenReturn(validPlaylistDTO);
		
		ResponseEntity<PlaylistDTO> response =
				new ResponseEntity<>(validPlaylistDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(playlistController.read(1L));
		
		verify(playlistService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(playlistService.create(Mockito.any(Playlist.class), user)).thenReturn(validPlaylistDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<PlaylistDTO> response = 
				new ResponseEntity<>(validPlaylistDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(playlistController.create(validPlaylist, "sessid"));
		
		verify(playlistService, times(1)).create(Mockito.any(Playlist.class), user);
		verify(sessions, times(1)).getSession(any(String.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(playlistService.update(validPlaylist, validPlaylist.getId(), user))
			 .thenReturn(validPlaylistDTO);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<PlaylistDTO> response =
				new ResponseEntity<>(validPlaylistDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(playlistController.update(validPlaylist, validPlaylist.getId(), "user"));
		
		verify(playlistService, times(1)).update(validPlaylist, validPlaylist.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}
	
	@Test
	void deleteGenre() {
		
		when(playlistService.delete(validPlaylist.getId(), user)).thenReturn(true);
		when(sessions.getSession(any(String.class))).thenReturn(user);
		
		ResponseEntity<Boolean> response =
				new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(playlistController.delete(validPlaylist.getId(), "user"));
		
		verify(playlistService, times(1)).delete(validPlaylist.getId(), user);
		verify(sessions, times(1)).getSession(any(String.class));
	}

}
