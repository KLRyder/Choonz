package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class artistDTOUnitTest {
	
	private final PlaylistDTO playlist = new PlaylistDTO();
	private final AlbumDTO album = new AlbumDTO();
	private final GenreDTO genre = new GenreDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(ArtistDTO.class)
				.withPrefabValues
					(AlbumDTO.class,
							new AlbumDTO(1, "name", null, genre, "cover"),
							new AlbumDTO(2, "name", null, genre, "cover"))
				
				.withPrefabValues
					(TrackDTO.class,
							new TrackDTO(1, "name", album, playlist, 300, "lyrics", null, genre),
							new TrackDTO(2, "name", album, playlist, 300, "lyrics", null, genre))
				.verify();
					
	}
	
	@Test
	void constructorTest() {
		ArtistDTO newArtistDTO = new ArtistDTO(1, "name");
		newArtistDTO.toString();
	}

}
