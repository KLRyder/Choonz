package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;

@SpringBootTest
public class artistServiceUnitTest {
	
	@Autowired
	private ArtistService artistService;
	
	@MockBean
	private ArtistRepository artistRepo;
	
	@MockBean
	private ArtistMapper artistMapper;
	
	private List<Artist> validArtists;
	private List<ArtistDTO> validArtistDTOs;
	
	private Artist validArtist;
	private ArtistDTO validArtistDTO;
	
	@BeforeEach
	void init() {
		validArtist = new Artist(1, "Rick", "Password");
		validArtistDTO = new ArtistDTO(1, "Rick");
		
		validArtists = new ArrayList<Artist>();
		validArtistDTOs = new ArrayList<ArtistDTO>();
		validArtists.add(validArtist);
		validArtistDTOs.add(validArtistDTO);
	}
	
	@Test
	void readAllArtists() {
		
	}
	
	@Test
	void readArtistByID() {
		
	}
	
	@Test
	void createArtist() {
		
	}
	
	@Test
	void updateArtist() {
		
	}
	
	@Test
	void deleteArtist() {
		
		when(artistRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		
		assertThat(false).isEqualTo(artistService.delete(validArtist.getId()));
		
		verify(artistRepo, times(1)).existsById(Mockito.any(Long.class));
		
	}

}
