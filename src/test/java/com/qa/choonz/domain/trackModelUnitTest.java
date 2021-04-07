package com.qa.choonz.domain;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class trackModelUnitTest {
	
	private final Artist artist = new Artist();
	private final Genre genre = new Genre();
	private final Album album = new Album();
	private final Playlist playlist = new Playlist();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(Track.class)
			.withPrefabValues(Album.class,
					new Album(1, "name1", artist, genre, "cover"),
					new Album(1, "name2", artist, genre, "cover"))
			
			.withPrefabValues(Playlist.class,
					new Playlist(1, "name", "description", "artwork"),
					new Playlist(2, "name2", "description2", "artwork"))
		.verify();
	}
	
	@Test
	void constructorTest() {
		Track newTrackModel = new Track(1, "name", album, playlist, 300, "lyrics");
		newTrackModel.toString();
	}

}
