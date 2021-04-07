package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;

@SpringBootTest
public class genreServiceIntegrationTest {
	
	@Autowired
	private GenreService genreService;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private GenreMapper genreMapper;

	@SuppressWarnings("unused")
	private List<Genre> genres;
	private List<GenreDTO> genreDTOs;

	private Genre validGenre;
	@SuppressWarnings("unused")
	private GenreDTO validGenreDTO;

	@BeforeEach
	public void init() {
		validGenre = new Genre();

		genres = new ArrayList<Genre>();
		genreDTOs = new ArrayList<GenreDTO>();

		genreRepository.deleteAll();

		validGenre = genreRepository.save(validGenre);

		validGenreDTO = genreMapper.mapToDeepDTO(validGenre);
	}

	@Test
	public void readAllGenresTest() {

		List<GenreDTO> genresInDB = genreService.read();

		assertThat(genreDTOs).isEqualTo(genresInDB);
	}

	@Test
	public void readByIdTest() {

		assertThat(validGenreDTO).isEqualTo(genreService.read(validGenre.getId()));

	}

	@Test
	public void updateGenre() {

		Genre updatedGenre = genreRepository.findAll().get(0);
		updatedGenre.setName("updated");
		GenreDTO updatedDTO = genreMapper.mapToShallowDTO(updatedGenre);
		assertThat(updatedDTO).isEqualTo(genreService.update(updatedGenre, updatedGenre.getId()));
	}

	@Test
	public void deleteGenre() {
		
		assertThat(genreService.delete(validGenre.getId())).isTrue();
	}

}