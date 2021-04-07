package com.qa.choonz.domain;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class playlistModelUnitTest {
	
	private final Album album = new Album();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(Playlist.class)
			.withPrefabValues(Track.class,
					new Track(1, "name1", album, null, 300, "lyrics"),
					new Track(1, "name2", album, null, 300, "lyrics"))
		.verify();
	}
	
	@Test
	void contructorTest() {
		Playlist newPlaylistModel = new Playlist(1, "name", "description", "artwork");
		newPlaylistModel.toString();
	}
	

}
