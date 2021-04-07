package com.qa.choonz.domain;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class albumModelUnitTest {
	
	private final Artist artist = new Artist();
	private final Genre genre = new Genre();
	private final Playlist playlist = new Playlist();
	private final Album album = new Album();
	
	@Test
	void testEquals() { 
		EqualsVerifier.simple().forClass(Album.class)
			.withPrefabValues(Track.class,
					new Track(1, "name1", null, playlist, 300, "lyrics"),
					new Track(2, "name2", null, playlist, 300, "lyrics"))
			.withPrefabValues(Artist.class,
					new Artist(1, "name1", "password"),
					new Artist(2, "name2", "password"))	
			.withPrefabValues(Genre.class,
					new Genre(1, "name1", "description"),
					new Genre(2, "name2", "description"))
			.verify();
	}
	
	@Test
	void constructorTest() {
		Album newAlbumModel = new Album(1, "name", artist, genre, "cover");
		newAlbumModel.toString();
	}

}
