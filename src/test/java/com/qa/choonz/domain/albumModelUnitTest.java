package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.*;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.util.ArrayList;
import java.util.List;

public class albumModelUnitTest {

    private final Artist artist = new Artist();
    private final Playlist playlist = new Playlist();
    private final Album album = new Album();
    private final ArtistAlbumLink link = new ArtistAlbumLink();

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Album.class)
                .withPrefabValues(
                        Track.class,
                        new Track(1, "name1", null, 300, "lyrics"),
                        new Track(2, "name2", null, 300, "lyrics"))
                .withPrefabValues(
                        Artist.class,
                        new Artist(1, "name1"),
                        new Artist(2, "name2"))
                .withPrefabValues(
                        Genre.class,
                        new Genre(1, "name1", "description"),
                        new Genre(2, "name2", "description"))
                .withPrefabValues(
                        Album.class,
                        new Album(4,"name4",new ArrayList<>(),"album cover 4"),
                        new Album(5,"name5",new ArrayList<>(),"album cover 5")
                )
                .verify();
    }

    @Test
    void constructorTest() {
        Album newAlbumModel = new Album(1, "name", List.of(link), "cover");
        newAlbumModel.toString();
    }

}
