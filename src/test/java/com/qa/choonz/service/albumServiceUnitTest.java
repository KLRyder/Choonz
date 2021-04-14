package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.*;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
	
	Artist artist = new Artist(1, "Rick");
	ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
	
	Genre genre = new Genre(1, "Name", "GenDescrip");
	GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
	List<GenreDTO> genreDTOS = new ArrayList<>();
	private UserDetails user = new UserDetails();
	ArtistAlbumLink link;
	
	@BeforeEach
	void init() {
		
		artist = new Artist(1, "Rick");
		ArtistDTO artistDTO = new ArtistDTO(1, "Rick");
		
		Genre genre = new Genre(1, "Name", "GenDescrip");
		GenreDTO genreDTO = new GenreDTO(1, "Name", "GenDescrip");
		genreDTOS.clear();
		genreDTOS.add(genreDTO);

		link = new ArtistAlbumLink();

		validAlbum = new Album(1, "Name", List.of(link), "Cover");
		validAlbumDTO = new AlbumDTO(1, "Name", List.of(artistDTO), genreDTOS, "Cover");
		link.setAlbum(validAlbum);
		link.setArtist(artist);

		validAlbums = new ArrayList<>();
		validAlbumDTOs = new ArrayList<>();
		validAlbums.add(validAlbum);
		validAlbumDTOs.add(validAlbumDTO);

		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");
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
		
		when(albumRepo.findById(1L)).thenReturn(Optional.of(validAlbum));
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(validAlbumDTO).isEqualTo(albumService.read(1L));
		
		verify(albumRepo, times(1)).findById(1L);
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void createAlbum() {
		
		when(albumRepo.save(Mockito.any(Album.class))).thenReturn(validAlbum);
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(validAlbumDTO).isEqualTo(albumService.create(validAlbum, user));
		
		verify(albumRepo, times(1)).save(Mockito.any(Album.class));
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void updateAlbum() {
		
		Album updateAlbum = new Album(1, "newName", List.of(link), "newCover");
		AlbumDTO updateAlbumDTO = new AlbumDTO(1, "newName", List.of(artistDTO), genreDTOS, "newCover");
		
		when(albumRepo.findById(Mockito.any(Long.class)))
			 .thenReturn(Optional.of(validAlbum));
		
		when(albumRepo.save(Mockito.any(Album.class)))
			 .thenReturn(updateAlbum);
		
		when(albumMapper.mapToDeepDTO(Mockito.any(Album.class)))
			 .thenReturn(updateAlbumDTO);
		
		AlbumDTO testAlbumDTO = albumService.update(updateAlbum, validAlbum.getId(), user);
		
		assertThat(updateAlbumDTO).isEqualTo(testAlbumDTO);
		
		verify(albumRepo, times(1)).findById(Mockito.any(Long.class));
		verify(albumRepo, times(1)).save(Mockito.any(Album.class));
		verify(albumMapper, times(1)).mapToDeepDTO(Mockito.any(Album.class));
		
	}
	
	@Test
	void deleteAlbum() {
		
		when(albumRepo.existsById(Mockito.any(Long.class)))
			 .thenReturn(true, false);
		
		assertThat(false).isEqualTo(albumService.delete(validAlbum.getId(), user));
		
		verify(albumRepo, times(1)).existsById(Mockito.any(Long.class));
	}

}
