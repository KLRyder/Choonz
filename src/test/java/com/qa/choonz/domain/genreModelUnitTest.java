package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class genreModelUnitTest {

    private final Artist artist = new Artist();

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Genre.class)
                .withPrefabValues(Album.class,
                        new Album(1, "name1", artist, "cover"),
                        new Album(2, "name2", artist, "cover"))
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
        newGenreModel.toString();
    }

}
