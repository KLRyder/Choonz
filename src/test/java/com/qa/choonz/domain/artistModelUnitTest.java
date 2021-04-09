package com.qa.choonz.domain;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.util.List;

public class artistModelUnitTest {

	
	@Test
	void testEquals() { 
		EqualsVerifier.simple().forClass(Artist.class)
			.withPrefabValues
				(Album.class,
					new Album(1, "name1", null, "cover"),
					new Album(2, "name2", null, "cover"))
			.verify();
	}
	
	@Test
	void constructorTest() {
		Artist newArtistModel = new Artist(1, "name");
		newArtistModel.toString();
	}

}
