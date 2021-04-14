package com.qa.choonz.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class playlistControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlaylistMapper mapper;

    private Cookie login;

    private Track validTrack;
    private Album validAlbum;
    private Genre validGenre;
    private Artist validArtist;
    private Playlist validPlaylist;
    private PlaylistDTO playlistDTO;
    private PlaylistLink validPlaylistLink;
    private ArtistAlbumLink validLink;
    private UserDetails adminUser;

    @BeforeEach
    public void setup() throws Exception {
        adminUser = new UserDetails();
        adminUser.setId(1);
        adminUser.setUsername("addy");
        adminUser.setPassword("pass");
        adminUser.setRole(UserRole.ADMIN);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/sessions/login");
        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(adminUser));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        mvc.perform(mockRequest).andDo(result -> login = result.getResponse().getCookies()[0]);

        validLink = new ArtistAlbumLink(1);
        validArtist = new Artist(1, "Artist name 1");
        validAlbum = new Album(1, "Album by artist 1", List.of(validLink), "Cover 1");
        validGenre = new Genre(1, "Genre name 1", "Genre description 1");
        validArtist.setAlbums(List.of(validLink));
        validLink.setAlbum(validAlbum);
        validLink.setArtist(validArtist);

        validTrack = new Track(1, "name1", validAlbum, 100, "lyrics1");
        validTrack.setGenre(validGenre);

        List<Track> tracks = new ArrayList<>();
        tracks.add(validTrack);
        validAlbum.setTracks(tracks);
        validGenre.setTracks(tracks);

        validPlaylistLink = new PlaylistLink();
        validPlaylist = new Playlist(1, "name1", "description1", "artwork1", List.of(validPlaylistLink), adminUser);
        validPlaylistLink.setPlaylist(validPlaylist);
        validPlaylistLink.setTrack(validTrack);

        playlistDTO = mapper.mapToDeepDTO(validPlaylist);
    }

    @Test
    public void createPlaylistTest() throws Exception {
        Playlist newPl = new Playlist(2,"new name", "new desc","new art", adminUser);
        PlaylistDTO expectedPlaylist = mapper.mapToDeepDTO(newPl);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/playlists/create");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(newPl));
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedPlaylist));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @Test
    public void readAllPlaylistsTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/playlists/read");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(playlistDTO)));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @Test
    public void readPlaylistByIdTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/playlists/read/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.queryParam("id", "1");

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(playlistDTO));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @Test
    public void updatePlaylistTest() throws Exception {
        validPlaylist.setArtwork("updated");
        validPlaylist.setName("new name");
        validPlaylistLink.setPlaylist(null);
        validGenre.setTracks(new ArrayList<>());
        validAlbum.setTracks(new ArrayList<>());
        PlaylistDTO expectedPlaylist = mapper.mapToDeepDTO(validPlaylist);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/playlists/update/1");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsBytes(validPlaylist));
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedPlaylist));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

    }

    @Test
    public void deletePlaylistTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/playlists/delete/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("");

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }
}

