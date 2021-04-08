package com.qa.choonz.rest.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:test-schema.sql", "classpath:test-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class trackControllerIntegrationTest {
	

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	private TrackDTO trackDTO = new TrackDTO();

	@Test
	public void createTrackTest() throws Exception {
		Track trackToSave = new Track();
		TrackDTO expectedTrack = new TrackDTO();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/track");

		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(objectMapper.writeValueAsString(trackToSave));
		mockRequest.accept(MediaType.APPLICATION_JSON);

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(expectedTrack));
		ResultMatcher headerMatcher = MockMvcResultMatchers.header().string("Location",
				String.valueOf(expectedTrack.getId()));

		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher).andExpect(headerMatcher);
	}

	@Test
	public void readAllTracksTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/track");
		mockRequest.accept(MediaType.APPLICATION_JSON);

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(trackDTO));

		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

	@Test
	public void readTrackByIdTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/track/alt");
		mockRequest.accept(MediaType.APPLICATION_JSON);
		mockRequest.queryParam("id", "1");

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(trackDTO));

		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

	@Test
	public void getTrackByNameTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"/track/name/RickRoll");
		mockRequest.accept(MediaType.APPLICATION_JSON);

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(trackDTO));
		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

	}

	@Test
	public void updateTrackTest() throws Exception {
		Track updatedTrack = new Track();
		TrackDTO expectedTrack = new TrackDTO();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/track/1");

		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(objectMapper.writeValueAsBytes(updatedTrack));

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(expectedTrack));

		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

	}

	@Test
	public void deleteTrackTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/track/1");
		mockRequest.accept(MediaType.APPLICATION_JSON);

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("true");

		mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

}
