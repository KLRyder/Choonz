package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class albumServiceIntegrationTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @SuppressWarnings("unused")
    private List<Album> albums;
    private List<AlbumDTO> albumDTOs;

    private Album validAlbum;
    @SuppressWarnings("unused")
    private AlbumDTO validAlbumDTO;

    private UserDetails user;

    @BeforeEach
    public void init() {
        validAlbum = new Album();

        albums = new ArrayList<>();
        albumDTOs = new ArrayList<>();

        albumRepository.deleteAll();

        validAlbum = albumRepository.save(validAlbum);

        validAlbumDTO = albumMapper.mapToDeepDTO(validAlbum);

        user = new UserDetails();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setPassword("pass");
        user.setUsername("addy the admin");
    }

    @Test
    public void readAllAlbumsTest() {

        List<AlbumDTO> albumsInDB = albumService.read();

        assertThat(albumDTOs).isEqualTo(albumsInDB);
    }

    @Test
    public void readByIdTest() {

        assertThat(validAlbumDTO).isEqualTo(albumService.read(validAlbum.getId()));

    }

    @Test
    public void updateAlbum() {

        Album updatedAlbum = albumRepository.findAll().get(0);
        updatedAlbum.setName("updated");
        AlbumDTO updatedDTO = albumMapper.mapToShallowDTO(updatedAlbum);
        assertThat(updatedDTO).isEqualTo(albumService.update(updatedAlbum, updatedAlbum.getId(), user));
    }

    @Test
    public void deleteAlbum() {

        assertThat(albumService.delete(validAlbum.getId(), user)).isTrue();
    }

}