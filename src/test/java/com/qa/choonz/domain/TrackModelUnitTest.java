package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TrackModelUnitTest {

    private final Album album = new Album();

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Track.class)
                .withPrefabValues(Album.class,
                        new Album(1, "name1", new ArrayList<>(), "cover"),
                        new Album(1, "name2", new ArrayList<>(), "cover"))
                .withPrefabValues(Playlist.class,
                        new Playlist(1, "name", "description", "artwork", new UserDetails()),
                        new Playlist(2, "name2", "description2", "artwork", new UserDetails()))
                .withPrefabValues(Genre.class,
                        new Genre(1,"genre 1","desc 1",new ArrayList<>()),
                        new Genre(2,"genre 2","desc 2",new ArrayList<>())
                        )
                .verify();
    }

    @Test
    void constructorTest() {
        Track newTrackModel = new Track(1, "name", album, 300, "lyrics");
        Assertions.assertTrue(newTrackModel.toString().contains("name"));
    }

}
