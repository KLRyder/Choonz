package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class playlistServiceIntegrationTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMapper playlistMapper;

    private Playlist validPlaylist;
    private PlaylistDTO validPlaylistDTO;
    private ArtistAlbumLink validLink;
    private Album validAlbum;
    private Genre validGenre;
    private Artist validArtist;

    private UserDetails user;

    @BeforeEach
    public void init() {

        validLink = new ArtistAlbumLink(1);
        validArtist = new Artist(1, "Artist name 1");
        validAlbum = new Album(1, "Album by artist 1", List.of(validLink), "Cover 1");
        validGenre = new Genre(1, "Genre name 1", "Genre description 1");
        validArtist.setAlbums(List.of(validLink));
        validLink.setAlbum(validAlbum);
        validLink.setArtist(validArtist);

        Track validTrack = new Track(1, "name1", validAlbum, 100, "lyrics1");
        validTrack.setGenre(validGenre);

        List<Track> tracks = new ArrayList<>();
        tracks.add(validTrack);
        validAlbum.setTracks(tracks);
        validGenre.setTracks(tracks);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");

        PlaylistLink pll = new PlaylistLink();
        validPlaylist = new Playlist(1L,"name1","description1","artwork1",List.of(pll),user);
        pll.setTrack(validTrack);
        pll.setPlaylist(validPlaylist);
        validPlaylistDTO = playlistMapper.mapToDeepDTO(validPlaylist);
    }

    @Test
    public void readAllPlaylistsTest() {

        assertThat(playlistService.read()).isEqualTo(List.of(validPlaylistDTO));
    }

    @Test
    public void readByIdTest() {

        assertThat(playlistService.read(validPlaylist.getId())).isEqualTo(validPlaylistDTO);

    }

    @Test
    public void updatePlaylist() {

        Playlist updatedPlaylist = playlistRepository.findAll().get(0);
        updatedPlaylist.setName("updated");
        PlaylistDTO updatedDTO = playlistMapper.mapToDeepDTO(updatedPlaylist);
        assertThat(playlistService.update(updatedPlaylist, updatedPlaylist.getId(), user)).isEqualTo(updatedDTO);
    }

    @Test
    public void deletePlaylist() {

        assertThat(playlistService.delete(validPlaylist.getId(), user)).isTrue();
    }

}