package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;


@SpringBootTest
public class albumServiceIntegrationTest {
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private AlbumMapper albumMapper;
	
	@SuppressWarnings("unused")
	private List<Album> albums;
	private List<AlbumDTO> albumDTOs;
	
	private Album validAlbum;
	@SuppressWarnings("unused")
	private AlbumDTO validAlbumDTO;
	
	@BeforeEach
	public void init() {
		validAlbum = new Album();
		
		albums = new ArrayList<Album>();
		albumDTOs = new ArrayList<AlbumDTO>();
		
		albumRepository.deleteAll();
		
		validAlbum = albumRepository.save(validAlbum);
		
		validAlbumDTO = albumMapper.mapToDeepDTO(validAlbum);
	}
	
	@Test
	public void readAllAlbumsTest() {
	
	List<AlbumDTO> albumsInDB = albumService.read();
	
	assertThat(albumDTOs).isEqualTo(albumsInDB);
	
}
}