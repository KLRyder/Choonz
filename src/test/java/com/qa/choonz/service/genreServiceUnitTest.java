package com.qa.choonz.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;


@SpringBootTest
public class genreServiceUnitTest {
	
	@Autowired
	private GenreService genreService;
	
	@MockBean
	private GenreRepository genreRepo;
	
	@MockBean
	private GenreMapper genreMapper;
	
	private List<Genre> validGenres;
	private List<GenreDTO> validGenreDTOs;
	
	private Genre validGenre;
	private GenreDTO validGenreDTO;
	
	@BeforeEach
	public void init() { 
		validGenre = new Genre(1L, "GenreName", "GenreDescrip");
		//validGenreDTO = new GenreDTO(1, "ListMe");
		
		validGenres = new ArrayList<Genre>();
		validGenreDTOs = new ArrayList<GenreDTO>();
		validGenres.add(validGenre);
		validGenreDTOs.add(validGenreDTO);
		
	}

}
