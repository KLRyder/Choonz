package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class albumDTOUnitTest {
	
	private final ArtistDTO artist = new ArtistDTO();
	private final GenreDTO genre = new GenreDTO();
	private final PlaylistDTO playlist = new PlaylistDTO();
	private final AlbumDTO album = new AlbumDTO();
	
	@Test
	void testEquals() { 
		EqualsVerifier.simple().forClass(AlbumDTO.class)
			.withPrefabValues(TrackDTO.class,
					new TrackDTO(1, "name", null, playlist, 300, "lyrics", artist, genre),
					new TrackDTO(2, "name", null, playlist, 300, "lyrics", artist, genre))
			.withPrefabValues(ArtistDTO.class,
					new ArtistDTO(1, "name1"),
					new ArtistDTO(2, "name2"))	
			.withPrefabValues(GenreDTO.class,
					new GenreDTO(1, "name1", "description"),
					new GenreDTO(2, "name2", "description"))
			.verify();
	}
	
	@Test
	void constructorTest() {
		AlbumDTO newAlbumModel = new AlbumDTO(1, "name", artist, genre, "cover");
		newAlbumModel.toString();
	}

}
