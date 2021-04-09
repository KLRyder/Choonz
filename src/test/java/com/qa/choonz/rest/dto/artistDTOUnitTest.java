package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.util.ArrayList;
import java.util.List;

public class artistDTOUnitTest {
	
	private final PlaylistDTO playlist = new PlaylistDTO();
	private final AlbumDTO album = new AlbumDTO();
	private final GenreDTO genre = new GenreDTO();
	private final List<GenreDTO> genres = new ArrayList<>();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(ArtistDTO.class)
				.withPrefabValues
					(AlbumDTO.class,
							new AlbumDTO(1, "name", null, genres, "cover"),
							new AlbumDTO(2, "name", null, genres, "cover"))
				
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
