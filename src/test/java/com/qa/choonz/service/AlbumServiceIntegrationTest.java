package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
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
public class AlbumServiceIntegrationTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    private List<Album> albums;
    private List<AlbumDTO> albumDTOs;

    private Album validAlbum;
    private AlbumDTO validAlbumDTO;

    private Artist validArtist;
    private Genre validGenre;
    private ArtistAlbumLink validLink;

    private UserDetails user;

    @BeforeEach
    public void init() {
        validAlbum = new Album();

        albums = new ArrayList<>();
        albumDTOs = new ArrayList<>();

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

        validAlbumDTO = albumMapper.mapToDeepDTO(validAlbum);

        albums.add(validAlbum);
        albumDTOs.add(validAlbumDTO);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllAlbumsTest() {
        assertThat(albumService.read()).isEqualTo(albumDTOs);
    }

    @Test
    public void readByIdTest() {
        assertThat(albumService.read(validAlbum.getId())).isEqualTo(validAlbumDTO);
    }

    @Test
    public void updateAlbum() {

        Album updatedAlbum = albumRepository.findAll().get(0);
        updatedAlbum.setName("updated");
        AlbumDTO updatedDTO = albumMapper.mapToDeepDTO(updatedAlbum);
        assertThat(albumService.update(updatedAlbum, updatedAlbum.getId(), user)).isEqualTo(updatedDTO);
    }

    @Test
    public void deleteAlbum() {

        assertThat(albumService.delete(validAlbum.getId(), user)).isTrue();
    }

}