package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.util.ArrayList;

public class GenreDTOUnitTest {
	
	private final PlaylistDTO playlist = new PlaylistDTO();
	private final AlbumDTO album = new AlbumDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(GenreDTO.class)
				.withPrefabValues
					(AlbumDTO.class, 
							new AlbumDTO(1, "name", new ArrayList<>(), null, "cover"),
							new AlbumDTO(2, "name", new ArrayList<>(), null, "cover"))
			
				.withPrefabValues
					(TrackDTO.class, 
							new TrackDTO(1, "name", album, playlist, 300, "lyrics", new ArrayList<>(), null),
							new TrackDTO(2, "name", album, playlist, 300, "lyrics", new ArrayList<>(), null))
				.verify();
	}
	
	@Test
	void constructorTest() {
		GenreDTO newGenreModel = new GenreDTO(1, "Rick", "RickandRoll");
		Assertions.assertTrue(newGenreModel.toString().contains("name"));
		
	}

}
