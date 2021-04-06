package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
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
		
		when(artistRepo.findAll()).thenReturn(validArtists);
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		assertThat(validArtistDTOs).isEqualTo(artistService.read());
		
		verify(artistRepo, times(1)).findAll();
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));
		
	}
	
	@Test
	void readArtistByID() {
		
		when(artistRepo.findById(1L)).thenReturn(Optional.of(validArtist));
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		assertThat(validArtistDTOs).isEqualTo(artistService.read(1L));
		
		verify(artistRepo, times(1)).findById(1L);
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));
		
	}
	
	@Test
	void createArtist() {
		
		when(artistRepo.save(Mockito.any(Artist.class))).thenReturn(validArtist);
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		assertThat(validArtistDTO).isEqualTo(artistService.create(validArtist));
		
		verify(artistRepo, times(1)).save(Mockito.any(Artist.class));
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));		
		
	}
	
	@Test
	void updateArtist() {
		
		Artist updateArtist = new Artist(1, "Ricky", "Password");
		ArtistDTO updateArtistDTO = new ArtistDTO(1, "Ricky");
		
		when(artistRepo.findById(Mockito.any(Long.class)))
			.thenReturn(Optional.of(validArtist));
		
		when(artistRepo.save(Mockito.any(Artist.class)))
			.thenReturn(updateArtist);
		
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class)))
			.thenReturn(updateArtistDTO);
		
		ArtistDTO testArtistDTO = artistService.update(updateArtist, validArtist.getId());
		
		assertThat(updateArtistDTO).isEqualTo(testArtistDTO);
		
		verify(artistRepo, times(1)).findById(Mockito.any(Long.class));
		verify(artistRepo, times(1)).save(Mockito.any(Artist.class));
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));
		
	}
	
	@Test
	void deleteArtist() {
		
		when(artistRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		
		assertThat(false).isEqualTo(artistService.delete(validArtist.getId()));
		
		verify(artistRepo, times(1)).existsById(Mockito.any(Long.class));
		
	}

}
