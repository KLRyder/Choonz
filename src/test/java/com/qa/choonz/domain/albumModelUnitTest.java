package com.qa.choonz.domain;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;

import nl.jqno.equalsverifier.EqualsVerifier;

public class albumModelUnitTest {
	
	@Test
	void testEquals() { 
		EqualsVerifier.simple().forClass(Album.class)
		.withPrefabValues(Album.class, new Album(1, "Rock", "Jimmy", "RockyRoll", ))
	}

}
