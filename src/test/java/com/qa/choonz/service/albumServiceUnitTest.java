package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;

@ExtendWith({MockitoExtension.class})
public class albumServiceUnitTest {
	
	@InjectMocks
	private AlbumService albumService;
	
	@Mock
	private AlbumRepository albumRepo;
	
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
	void readAllAlbums() {
		
		when(albumRepo.findAll()).thenReturn(validAlbums);
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(validAlbumDTOs).isEqualTo(albumService.read());
		
		verify(albumRepo, times(1)).findAll();
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void readAlbumByID() {
		
	}
	
	@Test
	void createAlbum() {
		
		when(albumRepo.save(Mockito.any(Album.class))).thenReturn(validAlbum);
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(validAlbumDTO).isEqualTo(albumService.create(validAlbum));
		
		verify(albumRepo, times(1)).save(Mockito.any(Album.class));
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void updateAlbum() {
		
		Album updateAlbum = new Album(1, "newName", artist, genre, "newCover");
		AlbumDTO updateAlbumDTO = new AlbumDTO(1, "newName", artistDTO, genreDTO, "newCover");
		
		when(albumRepo.findById(Mockito.any(Long.class)))
			 .thenReturn(Optional.of(validAlbum));
		
		when(albumRepo.save(Mockito.any(Album.class)))
			 .thenReturn(updateAlbum);
		
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class)))
			 .thenReturn(updateAlbumDTO);
		
		AlbumDTO testAlbumDTO = albumService.update(updateAlbum, validAlbum.getId());
		
		assertThat(updateAlbumDTO).isEqualTo(testAlbumDTO);
		
		verify(albumRepo, times(1)).findById(Mockito.any(Long.class));
		verify(albumRepo, times(1)).save(Mockito.any(Album.class));
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void deleteAlbum() {
		
		when(albumRepo.existsById(Mockito.any(Long.class)))
			 .thenReturn(true, false);
		
		assertThat(false).isEqualTo(albumService.delete(validAlbum.getId()));
		
		verify(albumRepo, times(1)).existsById(Mockito.any(Long.class));
	}

}
