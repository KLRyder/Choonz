package com.qa.choonz.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class PlaylistLink {
    @Id
    long id;

    @ManyToOne
    Playlist playlist;

    @ManyToOne
    Track track;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistLink)) return false;

        PlaylistLink that = (PlaylistLink) o;

        if (id != that.id) return false;
        if (!Objects.equals(playlist, that.playlist)) return false;
        return Objects.equals(track, that.track);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        return result;
    }
}
