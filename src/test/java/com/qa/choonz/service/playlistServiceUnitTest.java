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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;

@SpringBootTest
public class playlistServiceUnitTest {
	
	@Autowired
	private PlaylistService playlistService;
	
	@MockBean
	private PlaylistRepository playlistRepo;
	
	@MockBean
	private PlaylistMapper playlistMapper;
	
	private List<Playlist> validPlaylists;
	private List<PlaylistDTO> validPlaylistDTOs;
	
	private Playlist validPlaylist;
	private PlaylistDTO validPlaylistDTO;
	
	@BeforeEach
	void init() {
		validPlaylist = new Playlist(1, "Name" ,"PlaylistDescrip", "Artwork");
		validPlaylistDTO = new PlaylistDTO(1, "Name" ,"PlaylistDescrip", "Artwork");
		
		validPlaylists = new ArrayList<Playlist>();
		validPlaylistDTOs = new ArrayList<PlaylistDTO>();
		validPlaylists.add(validPlaylist);
		validPlaylistDTOs.add(validPlaylistDTO);
	}
	
	@Test
	void readAllPlaylist() {
		
		when(playlistRepo.findAll()).thenReturn(validPlaylists);
		when(playlistMapper.mapToShallowDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTOs).isEqualTo(playlistService.read());
		
		verify(playlistRepo, times(1)).findAll();
		verify(playlistMapper, times(1)).mapToShallowDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void readPlaylistByID() {
		
		when(playlistRepo.findById(1L)).thenReturn(Optional.of(validPlaylist));
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTOs).isEqualTo(playlistService.read(1L));
		
		verify(playlistRepo, times(1)).findById(1L);
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void createPlaylist() {
		
		when(playlistRepo.save(Mockito.any(Playlist.class))).thenReturn(validPlaylist);
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(validPlaylistDTO).isEqualTo(playlistService.create(validPlaylist));
		
		verify(playlistRepo, times(1)).save(Mockito.any(Playlist.class));
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
		
	}
	
	@Test
	void updatePlaylist() {
		
		Playlist updatePlaylist = new Playlist(1, "newName", "newDescrip", "newArt");
		PlaylistDTO updatePlaylistDTO = new PlaylistDTO(1, "newName", "newDescrip", "newArt");
		
		when(playlistRepo.findById(Mockito.any(Long.class)))
			 .thenReturn(Optional.of(validPlaylist));
		
		when(playlistRepo.save(Mockito.any(Playlist.class)))
			 .thenReturn(updatePlaylist);
		
		when(playlistMapper.mapToDeepDTO(Mockito.any(Playlist.class)))
			 .thenReturn(updatePlaylistDTO);
		
		PlaylistDTO testPlaylistDTO = playlistService.update(updatePlaylist, validPlaylist.getId());
		
		assertThat(updatePlaylistDTO).isEqualTo(testPlaylistDTO);
		
		verify(playlistRepo, times(1)).findById(Mockito.any(Long.class));
		verify(playlistRepo, times(1)).save(Mockito.any(Playlist.class));
		verify(playlistMapper, times(1)).mapToDeepDTO(Mockito.any(Playlist.class));
	}
	
	@Test
	void deletePlaylist() {
		
		when(playlistRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		
		assertThat(false).isEqualTo(playlistService.delete(validPlaylist.getId()));
		
		verify(playlistRepo, times(1)).existsById(Mockito.any(Long.class));
	}

}
