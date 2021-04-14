package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class artistModelUnitTest {


    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Artist.class)
                .withPrefabValues(
                        Album.class,
                        new Album(1, "name1", null, "cover"),
                        new Album(2, "name2", null, "cover"))
                .withPrefabValues(
                        Artist.class,
                        new Artist(4, "Artist 4"),
                        new Artist(5, "artist 5"))
                .verify();
    }

    @Test
    void constructorTest() {
        Artist newArtistModel = new Artist(1, "name");
        newArtistModel.toString();
    }

}
