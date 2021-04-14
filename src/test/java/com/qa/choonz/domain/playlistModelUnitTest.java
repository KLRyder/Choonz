package com.qa.choonz.domain;

import com.qa.choonz.persistence.domain.UserDetails;
import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class playlistModelUnitTest {

    private final Album album = new Album();

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Playlist.class)
                .withPrefabValues(Track.class,
                        new Track(1, "name1", album, 300, "lyrics"),
                        new Track(1, "name2", album, 300, "lyrics"))
                .withPrefabValues(Playlist.class,
                        new Playlist(1, "name", "description", "artwork", new UserDetails()),
                        new Playlist(2, "name2", "description2", "artwork", new UserDetails()))
                .verify();
    }

    @Test
    void constructorTest() {
        Playlist newPlaylistModel = new Playlist(1, "name", "description", "artwork", null);
        newPlaylistModel.toString();
    }


}
