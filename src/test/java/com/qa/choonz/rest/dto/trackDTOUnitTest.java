package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class trackDTOUnitTest {
	
	private final ArtistDTO artist = new ArtistDTO();
	private final GenreDTO genre = new GenreDTO();
	private final AlbumDTO album = new AlbumDTO();
	private final PlaylistDTO playlist = new PlaylistDTO();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(TrackDTO.class)
			.withPrefabValues(AlbumDTO.class,
					new AlbumDTO(1, "name1", artist, genre, "cover"),
					new AlbumDTO(1, "name2", artist, genre, "cover"))
			
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
		TrackDTO newTrackModel = new TrackDTO(1, "name", album, playlist, 300, "lyrics", artist, genre);
		newTrackModel.toString();
	}


}
