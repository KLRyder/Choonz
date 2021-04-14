package com.qa.choonz.rest.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class playlistDTOUnitTest {
	
	private final AlbumDTO album = new AlbumDTO();
	private final GenreDTO genre = new GenreDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(PlaylistDTO.class)
			.withPrefabValues(TrackDTO.class, 
					new TrackDTO(1, "name1", album, null, 300, "lyrics", new ArrayList<>(), genre),
					new TrackDTO(2, "name2", album, null, 300, "lyrics", new ArrayList<>(), genre))
			.verify();
	}
	
	@Test
	void constructorTest() {
		PlaylistDTO newPlaylistModel = new PlaylistDTO(1, "name", "description", "artwork");
		newPlaylistModel.toString();
	}

}
