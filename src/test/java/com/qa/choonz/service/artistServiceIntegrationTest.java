package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;

@SpringBootTest
public class artistServiceIntegrationTest {
	
	@Autowired
	private ArtistService artistService;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private ArtistMapper artistMapper;

	@SuppressWarnings("unused")
	private List<Artist> artists;
	private List<ArtistDTO> artistDTOs;

	private Artist validArtist;
	@SuppressWarnings("unused")
	private ArtistDTO validArtistDTO;

	@BeforeEach
	public void init() {
		validArtist = new Artist();

		artists = new ArrayList<Artist>();
		artistDTOs = new ArrayList<ArtistDTO>();

		artistRepository.deleteAll();

		validArtist = artistRepository.save(validArtist);

		validArtistDTO = artistMapper.mapToDeepDTO(validArtist);
	}

	@Test
	public void readAllArtistsTest() {

		List<ArtistDTO> artistsInDB = artistService.read();

		assertThat(artistDTOs).isEqualTo(artistsInDB);
	}

	@Test
	public void readByIdTest() {

		assertThat(validArtistDTO).isEqualTo(artistService.read(validArtist.getId()));

	}

	@Test
	public void updateArtist() {

		Artist updatedArtist = artistRepository.findAll().get(0);
		updatedArtist.setName("updated");
		ArtistDTO updatedDTO = artistMapper.mapToShallowDTO(updatedArtist);
		assertThat(updatedDTO).isEqualTo(artistService.update(updatedArtist, updatedArtist.getId()));
	}

	@Test
	public void deleteArtist() {
		
		assertThat(artistService.delete(validArtist.getId())).isTrue();
	}

}