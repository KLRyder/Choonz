package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenreModelUnitTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Genre.class)
                .withPrefabValues(Album.class,
                        new Album(1, "name1", List.of(new ArtistAlbumLink()), "cover"),
                        new Album(2, "name2", List.of(new ArtistAlbumLink()), "cover"))
                .withPrefabValues(
                        Track.class,
						new Track(1, "track1", null, 1, "lyrics 1"),
						new Track(2, "track2", null, 2, "lyrics 2")
                        )
                .verify();
    }

    @Test
    void constructorTest() {
        Genre newGenreModel = new Genre(1, "Rick", "RickandRoll", new ArrayList<>());
        Assertions.assertTrue(newGenreModel.toString().contains("name"));
    }

}
