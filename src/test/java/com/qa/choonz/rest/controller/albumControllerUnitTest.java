package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
import com.qa.choonz.service.AlbumService;

@ExtendWith({MockitoExtension.class})
public class albumControllerUnitTest {
	
	@InjectMocks
	private AlbumController albumController;
	
	@Mock
	private AlbumService albumService;
	
	@Mock
	private AlbumMapper albumMapper;
	
	private List<Album> validAlbums;
	private List<AlbumDTO> validAlbumDTOs;
	
	private Album validAlbum;
	private AlbumDTO validAlbumDTO;
	
	Artist artist = new Artist(1, "Rick", "Password");
	ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
	
	Genre genre = new Genre(1, "Name", "GenDescrip");
	GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
	
	@BeforeEach
	void init() {
		
		Artist artist = new Artist(1, "Rick", "Password");
		ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
		
		Genre genre = new Genre(1, "Name", "GenDescrip");
		GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
		
		validAlbum = new Album(1, "Name", artist, genre, "Cover");
		validAlbumDTO = new AlbumDTO(1, "Name", artistDTO, genreDTO, "Cover");
		
		validAlbums = new ArrayList<Album>();
		validAlbumDTOs = new ArrayList<AlbumDTO>();
		validAlbums.add(validAlbum);
		validAlbumDTOs.add(validAlbumDTO);
	}
	
	@Test
	void readAllGenres() {
		
		when(albumService.read()).thenReturn(validAlbumDTOs);
		
		ResponseEntity<List<AlbumDTO>> response = 
				new ResponseEntity<List<AlbumDTO>>(validAlbumDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(albumController.read());
		
		verify(albumService, times(1)).read();
	}
	
	@Test
	void readGenreByID() {
		
		when(albumService.read(1L)).thenReturn(validAlbumDTO);
		
		ResponseEntity<AlbumDTO> response =
				new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(albumController.read(1L));
		
		verify(albumService, times(1)).read(1L);
		
	}
	
	@Test
	void createGenre() {
		
		when(albumService.create(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		ResponseEntity<AlbumDTO> response = 
				new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(albumController.create(validAlbum));
		
		verify(albumService, times(1)).create(Mockito.any(Album.class));
		
	}
	
	@Test
	void updateGenre() {
		
		when(albumService.update(validAlbum, validAlbum.getId()))
			 .thenReturn(validAlbumDTO);
		
		ResponseEntity<AlbumDTO> response =
				new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(albumController.update(validAlbum, validAlbum.getId()));
		
		verify(albumService, times(1)).update(validAlbum, validAlbum.getId());
	}
	
	@Test
	void deleteGenre() {
		
		when(albumService.delete(validAlbum.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = 
				new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(albumController.delete(validAlbum.getId()));
		
		verify(albumService, times(1)).delete(validAlbum.getId());
	}

}
