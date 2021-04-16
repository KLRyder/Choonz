package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
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
public class PlaylistServiceUnitTest {
	
	@InjectMocks
	private PlaylistService playlistService;
	
	@Mock
	private PlaylistRepository playlistRepo;
	
	@Mock
	private PlaylistMapper playlistMapper;
	
	private List<Playlist> validPlaylists;
	private List<PlaylistDTO> validPlaylistDTOs;
	
	private Playlist validPlaylist;
	private PlaylistDTO validPlaylistDTO;

	private UserDetails user = new UserDetails();
	
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
	void readAllPlaylist() {
		
		when(playlistRepo.findAll()).thenReturn(validPlaylists);
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTOs).isEqualTo(playlistService.read());
		
		verify(playlistRepo, times(1)).findAll();
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void readPlaylistByID() {
		
		when(playlistRepo.findById(1L)).thenReturn(Optional.of(validPlaylist));
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTO).isEqualTo(playlistService.read(1L));
		
		verify(playlistRepo, times(1)).findById(1L);
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void createPlaylist() {
		
		when(playlistRepo.save(Mockito.any(Playlist.class))).thenReturn(validPlaylist);
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTO).isEqualTo(playlistService.create(validPlaylist, user));
		
		verify(playlistRepo, times(1)).save(Mockito.any(Playlist.class));
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void updatePlaylist() {
		
		Playlist updatePlaylist = new Playlist(1, "newName", "newDescrip", "newArt", user);
		PlaylistDTO updatePlaylistDTO = new PlaylistDTO(1, "newName", "newDescrip", "newArt");
		
		when(playlistRepo.findById(Mockito.any(Long.class)))
			 .thenReturn(Optional.of(validPlaylist));
		
		when(playlistRepo.save(Mockito.any(Playlist.class)))
			 .thenReturn(updatePlaylist);
		
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class)))
			 .thenReturn(updatePlaylistDTO);
		
		PlaylistDTO testPlaylistDTO = playlistService.update(updatePlaylist, validPlaylist.getId(), user);
		
		assertThat(updatePlaylistDTO).isEqualTo(testPlaylistDTO);
		
		verify(playlistRepo, times(1)).findById(Mockito.any(Long.class));
		verify(playlistRepo, times(1)).save(Mockito.any(Playlist.class));
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
	}
	
	@Test
	void deletePlaylist() {
		
		when(playlistRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		when(playlistRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(validPlaylist));
		
		assertThat(playlistService.delete(validPlaylist.getId(), user)).isEqualTo(false);
		
		verify(playlistRepo, times(1)).existsById(Mockito.any(Long.class));
		verify(playlistRepo, times(1)).findById(Mockito.any(Long.class));
	}

}
