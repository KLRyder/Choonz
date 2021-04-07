package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class genreDTOUnitTest {
	
	
	private final ArtistDTO artist = new ArtistDTO();
	private final PlaylistDTO playlist = new PlaylistDTO();
	private final AlbumDTO album = new AlbumDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(GenreDTO.class)
			.withPrefabValues
					(AlbumDTO.class, 
							new AlbumDTO(1, "name", artist, null, "cover"),
							new AlbumDTO(2, "name", artist, null, "cover"))
			
				.withPrefabValues
					(TrackDTO.class, 
							new TrackDTO(1, "name", album, playlist, 300, "lyrics", artist, null), 
							new TrackDTO(2, "name", album, playlist, 300, "lyrics", artist, null)) .verify();
	}
	
	@Test
	void constructorTest() {
		GenreDTO newGenreModel = new GenreDTO(1, "Rick", "RickandRoll");
		newGenreModel.toString();
		
	}

}
