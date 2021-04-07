package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;

@SpringBootTest
public class playlistServiceIntegrationTest {
	
	@Autowired
	private PlaylistService playlistService;

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private PlaylistMapper playlistMapper;

	@SuppressWarnings("unused")
	private List<Playlist> playlists;
	private List<PlaylistDTO> playlistDTOs;

	private Playlist validPlaylist;
	@SuppressWarnings("unused")
	private PlaylistDTO validPlaylistDTO;

	@BeforeEach
	public void init() {
		validPlaylist = new Playlist();

		playlists = new ArrayList<Playlist>();
		playlistDTOs = new ArrayList<PlaylistDTO>();

		playlistRepository.deleteAll();

		validPlaylist = playlistRepository.save(validPlaylist);

		validPlaylistDTO = playlistMapper.mapToDeepDTO(validPlaylist);
	}

	@Test
	public void readAllPlaylistsTest() {

		List<PlaylistDTO> playlistsInDB = playlistService.read();

		assertThat(playlistDTOs).isEqualTo(playlistsInDB);
	}

	@Test
	public void readByIdTest() {

		assertThat(validPlaylistDTO).isEqualTo(playlistService.read(validPlaylist.getId()));

	}

	@Test
	public void updatePlaylist() {

		Playlist updatedPlaylist = playlistRepository.findAll().get(0);
		updatedPlaylist.setName("updated");
		PlaylistDTO updatedDTO = playlistMapper.mapToShallowDTO(updatedPlaylist);
		assertThat(updatedDTO).isEqualTo(playlistService.update(updatedPlaylist, updatedPlaylist.getId()));
	}

	@Test
	public void deletePlaylist() {
		
		assertThat(playlistService.delete(validPlaylist.getId())).isTrue();
	}

}