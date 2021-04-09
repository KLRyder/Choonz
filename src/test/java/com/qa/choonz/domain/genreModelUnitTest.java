package com.qa.choonz.domain;

import java.util.ArrayList;

import com.qa.choonz.persistence.domain.Track;
import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;

import nl.jqno.equalsverifier.EqualsVerifier;

public class genreModelUnitTest {
	
	private final Artist artist = new Artist();
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(Genre.class)
			.withPrefabValues(Album.class, 
				new Album(1, "name1", artist, "cover"),
				new Album(2, "name2", artist, "cover"))
				.verify();
	}
	
	@Test
	void constructorTest() {
		Genre newGenreModel = new Genre(1, "Rick", "RickandRoll", new ArrayList<>());
		newGenreModel.toString();
	}

}
