package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Playlist;

import nl.jqno.equalsverifier.EqualsVerifier;

public class playlistDTOUnitTest {
	
	private final AlbumDTO album = new AlbumDTO();
	private final ArtistDTO artist = new ArtistDTO();
	private final GenreDTO genre = new GenreDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(PlaylistDTO.class)
			.withPrefabValues(TrackDTO.class, 
					new TrackDTO(1, "name1", album, null, 300, "lyrics", artist, genre), 
					new TrackDTO(2, "name2", album, null, 300, "lyrics", artist, genre))
			.verify();
	}
	
	@Test
	void contructorTest() {
		PlaylistDTO newPlaylistModel = new PlaylistDTO(1, "name", "description", "artwork");
		newPlaylistModel.toString();
	}

}
