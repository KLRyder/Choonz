package com.qa.choonz.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.rest.mapper.TrackMapper;
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
public class TrackControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrackMapper trackMapper;

    private Cookie login;

    private Track validTrack;
    private TrackDTO validTrackDTO;
    private Album validAlbum;
    private Genre validGenre;
    private Artist validArtist;
    private ArtistAlbumLink validLink;


    @BeforeEach
    public void setup() throws Exception {
        UserDetails adminUser = new UserDetails();
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

        validTrackDTO = trackMapper.mapToDeepDTO(validTrack);

        adminUser.setId(1);
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setPassword("pass");
        adminUser.setUsername("addy the admin");

    }

    @Test
    public void createTrackTest() throws Exception {
        Track trackToSave = new Track(2, "new name", validAlbum, 4884, "new lyrics");
        trackToSave.setGenre(validGenre);
        TrackDTO expectedTrack = trackMapper.mapToDeepDTO(trackToSave);
        //prevent infinite recursion with JSON
        validAlbum.setTracks(new ArrayList<>());
        validGenre.setTracks(new ArrayList<>());
        validArtist.setAlbums(new ArrayList<>());
        validAlbum.setArtists(new ArrayList<>());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/tracks/create");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(trackToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedTrack));

        System.out.println("test3");


        mvc.perform(mockRequest)
                .andExpect(statusMatcher)
                .andExpect(contentMatcher);
    }

    @Test
    public void readAllTracksTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/tracks/read");
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(validTrackDTO)));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }


    @Test
    public void readTrackByIdTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/tracks/read/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(validTrackDTO));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }


    @Test
    public void updateTrackTest() throws Exception {
        Track updatedTrack = validTrack;
        validAlbum.setArtists(new ArrayList<>());
        updatedTrack.setName("updated name");
        updatedTrack.setLyrics("updated lyrics");
        updatedTrack.setDuration(99999);
        TrackDTO expectedTrack = trackMapper.mapToDeepDTO(updatedTrack);
        //prevent infinite recursion with JSON
        validAlbum.setTracks(new ArrayList<>());
        validArtist.setAlbums(new ArrayList<>());
        validGenre.setTracks(new ArrayList<>());


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/tracks/update/1");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsBytes(updatedTrack));
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedTrack));

        System.out.println(expectedTrack);
        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

    }

    @Test
    public void deleteTrackTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/tracks/delete/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.cookie(login);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();
        ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("");

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }
}
