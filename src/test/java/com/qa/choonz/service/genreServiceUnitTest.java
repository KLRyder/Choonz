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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.GenreMapper;


@ExtendWith({MockitoExtension.class})
public class genreServiceUnitTest {
	
	@InjectMocks
	private GenreService genreService;
	
	@Mock
	private GenreRepository genreRepo;
	
	@Mock
	private GenreMapper genreMapper;
	
	private List<Genre> validGenres;
	private List<GenreDTO> validGenreDTOs;
	
	private Genre validGenre;
	private GenreDTO validGenreDTO;
	
	@BeforeEach
	void init() { 
		validGenre = new Genre(1L, "GenreName", "GenreDescrip");
		validGenreDTO = new GenreDTO(1, "GenreName", "GenreDescrip");
		
		validGenres = new ArrayList<Genre>();
		validGenreDTOs = new ArrayList<GenreDTO>();
		validGenres.add(validGenre);
		validGenreDTOs.add(validGenreDTO);
		
	}
	
	@Test
	void readGenreByID() {
		
		when(genreRepo.findById(1L)).thenReturn(Optional.of(validGenre));
		when(genreMapper.mapToDeepDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTO);
		
		assertThat(validGenreDTO).isEqualTo(genreService.read(1L));
		
		verify(genreRepo, times(1)).findById(1L);
		verify(genreMapper, times(1)).mapToDeepDTO(Mockito.any(Genre.class));
		
	}
	
	@Test
	void readAllGenre() {
		
		when(genreRepo.findAll()).thenReturn(validGenres);
		when(genreMapper.mapToDeepDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTO);
		
		assertThat(validGenreDTOs).isEqualTo(genreService.read());
		
		verify(genreRepo, times(1)).findAll();
		verify(genreMapper, times(1)).mapToDeepDTO(Mockito.any(Genre.class));
		
	}
	
	@Test
	void createGenre() {
		
		when(genreRepo.save(Mockito.any(Genre.class))).thenReturn(validGenre);
		when(genreMapper.mapToDeepDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTO);
		
		assertThat(validGenreDTO).isEqualTo(genreService.create(validGenre));
		
		verify(genreRepo, times(1)).save(Mockito.any(Genre.class));
		verify(genreMapper, times(1)).mapToDeepDTO(Mockito.any(Genre.class));
		
	}
	
	@Test
	void updateGenre() {
		
		Genre updateGenre = new Genre(1L, "newGenreName", "newGenreDescrip");
		GenreDTO updateGenreDTO = new GenreDTO(1L, "newGenreName", "newGenreDescrip");
		
		when(genreRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(validGenre));
		when(genreRepo.save(Mockito.any(Genre.class))).thenReturn(updateGenre);
		when(genreMapper.mapToDeepDTO(Mockito.any(Genre.class))).thenReturn(updateGenreDTO);
		
		GenreDTO testGenreDTO = genreService.update(updateGenre, validGenre.getId());
		
		assertThat(updateGenreDTO).isEqualTo(testGenreDTO);
		
		verify(genreRepo, times(1)).findById(Mockito.any(Long.class));
		verify(genreRepo, times(1)).save(Mockito.any(Genre.class));
		verify(genreMapper, times(1)).mapToDeepDTO(Mockito.any(Genre.class));
	}
	
	@Test
	void deleteGenre() {
		
		when(genreRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		
		assertThat(false).isEqualTo(genreService.delete(validGenre.getId()));
		
		verify(genreRepo, times(1)).existsById(Mockito.any(Long.class));
	}

}
