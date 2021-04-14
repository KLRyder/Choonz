package com.qa.choonz.rest.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class albumDTOUnitTest {
	
	private final GenreDTO genre = new GenreDTO();
	private final List<GenreDTO> genreDTOS = new ArrayList<>();
	private final PlaylistDTO playlist = new PlaylistDTO();

	@Test
	void testEquals() { 
		EqualsVerifier.simple().forClass(AlbumDTO.class)
			.withPrefabValues(TrackDTO.class,
					new TrackDTO(1, "name", null, playlist, 300, "lyrics", new ArrayList<>(), genre),
					new TrackDTO(2, "name", null, playlist, 300, "lyrics", new ArrayList<>(), genre))
			.withPrefabValues(ArtistDTO.class,
					new ArtistDTO(1, "name1"),
					new ArtistDTO(2, "name2"))	
			.withPrefabValues(GenreDTO.class,
					new GenreDTO(1, "name1", "description"),
					new GenreDTO(2, "name2", "description"))
			.verify();
	}
	
	@Test
	void constructorTest() {
		genreDTOS.add(genre);
		AlbumDTO newAlbumModel = new AlbumDTO(1, "name", new ArrayList<>(), genreDTOS, "cover");
		newAlbumModel.toString();
	}

}
