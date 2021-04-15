package com.qa.choonz.rest.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrackDTOUnitTest {
	
	private final List<GenreDTO> genres = new ArrayList<>();
	private final AlbumDTO album = new AlbumDTO();
	private final PlaylistDTO playlist = new PlaylistDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(TrackDTO.class)
			.withPrefabValues(AlbumDTO.class,
					new AlbumDTO(1, "name1", new ArrayList<>(), genres, "cover"),
					new AlbumDTO(1, "name2", new ArrayList<>(), genres, "cover"))
			
			.withPrefabValues(PlaylistDTO.class,
					new PlaylistDTO(1, "name", "description", "artwork"),
					new PlaylistDTO(2, "name2", "description2", "artwork"))
			
			.withPrefabValues(ArtistDTO.class, 
					new ArtistDTO(1, "name"), 
					new ArtistDTO(2, "name2"))
			
			.withPrefabValues(GenreDTO.class,
					new GenreDTO(1, "name", "description"), 
					new GenreDTO(1, "name2", "description"))
		.verify();
	}
	
	@Test
	void constructorTest() {
		TrackDTO newTrackModel = new TrackDTO(1, "name", album, playlist, 300, "lyrics", new ArrayList<>(), new GenreDTO());
		Assertions.assertTrue(newTrackModel.toString().contains("name"));
	}

}
