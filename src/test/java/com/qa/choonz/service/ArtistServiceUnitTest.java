package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.mapper.ArtistMapper;
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
public class ArtistServiceUnitTest {
	
	@InjectMocks
	private ArtistService artistService;
	
	@Mock
	private ArtistRepository artistRepo;
	
	@Mock
	private ArtistMapper artistMapper;
	
	private List<Artist> validArtists;
	private List<ArtistDTO> validArtistDTOs;
	
	private Artist validArtist;
	private ArtistDTO validArtistDTO;
	private UserDetails user = new UserDetails();
	
	@BeforeEach
	void init() {
		validArtist = new Artist(1, "Rick");
		validArtistDTO = new ArtistDTO(1, "Rick");
		
		validArtists = new ArrayList<>();
		validArtistDTOs = new ArrayList<>();
		validArtists.add(validArtist);
		validArtistDTOs.add(validArtistDTO);

		user.setId(1);
		user.setRole(UserRole.ADMIN);
		user.setPassword("pass");
		user.setUsername("addy the admin");
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
		
		assertThat(validArtistDTO).isEqualTo(artistService.read(1L));
		
		verify(artistRepo, times(1)).findById(1L);
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));
		
	}
	
	@Test
	void createArtist() {
		
		when(artistRepo.save(Mockito.any(Artist.class))).thenReturn(validArtist);
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		assertThat(validArtistDTO).isEqualTo(artistService.create(validArtist, user));
		
		verify(artistRepo, times(1)).save(Mockito.any(Artist.class));
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));		
		
	}
	
	@Test
	void updateArtist() {
		
		Artist updateArtist = new Artist(1, "Ricky");
		ArtistDTO updateArtistDTO = new ArtistDTO(1, "Ricky");
		
		when(artistRepo.findById(Mockito.any(Long.class)))
			.thenReturn(Optional.of(validArtist));
		
		when(artistRepo.save(Mockito.any(Artist.class)))
			.thenReturn(updateArtist);
		
		when(artistMapper.mapToDeepDTO(Mockito.any(Artist.class)))
			.thenReturn(updateArtistDTO);
		
		ArtistDTO testArtistDTO = artistService.update(updateArtist, validArtist.getId(), user);
		
		assertThat(updateArtistDTO).isEqualTo(testArtistDTO);
		
		verify(artistRepo, times(1)).findById(Mockito.any(Long.class));
		verify(artistRepo, times(1)).save(Mockito.any(Artist.class));
		verify(artistMapper, times(1)).mapToDeepDTO(Mockito.any(Artist.class));
		
	}
	
	@Test
	void deleteArtist() {
		
		when(artistRepo.existsById(Mockito.any(Long.class))).thenReturn(true, false);
		
		assertThat(artistService.delete(validArtist.getId(), user)).isEqualTo(false);
		
		verify(artistRepo, times(1)).existsById(Mockito.any(Long.class));
	}

}
