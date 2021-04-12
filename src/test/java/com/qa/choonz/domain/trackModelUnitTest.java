package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.*;
import org.junit.jupiter.api.Test;

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
					new Album(1, "name1", artist, "cover"),
					new Album(1, "name2", artist, "cover"))
			
			.withPrefabValues(Playlist.class,
					new Playlist(1, "name", "description", "artwork", new UserDetails()),
					new Playlist(2, "name2", "description2", "artwork", new UserDetails()))
		.verify();
	}
	
	@Test
	void constructorTest() {
		Track newTrackModel = new Track(1, "name", album, 300, "lyrics");
		newTrackModel.toString();
	}

}
