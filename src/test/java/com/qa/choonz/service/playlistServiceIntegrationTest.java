package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    private UserDetails user;

    @BeforeEach
    public void init() {
        validPlaylist = new Playlist();

        playlists = new ArrayList<>();
        playlistDTOs = new ArrayList<>();

        playlistRepository.deleteAll();

        validPlaylist = playlistRepository.save(validPlaylist);

        validPlaylistDTO = playlistMapper.mapToDeepDTO(validPlaylist);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
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
        assertThat(updatedDTO).isEqualTo(playlistService.update(updatedPlaylist, updatedPlaylist.getId(), user));
    }

    @Test
    public void deletePlaylist() {

        assertThat(playlistService.delete(validPlaylist.getId(), user)).isTrue();
    }

}