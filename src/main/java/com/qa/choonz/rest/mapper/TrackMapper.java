package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;

public class TrackMapper {
    public static TrackDTO mapToDeepDTO(Track track) {
        TrackDTO toReturn = new TrackDTO();

        toReturn.setId(track.getId());
        toReturn.setName(track.getName());
        toReturn.setDuration(track.getDuration());
        toReturn.setLyrics(track.getLyrics());
        toReturn.setAlbum(AlbumMapper.mapToShallowDTO(track.getAlbum()));
        toReturn.setArtist(ArtistMapper.mapToShallowDTO(track.getAlbum().getArtist()));
        toReturn.setGenre(GenreMapper.mapToShallowDTO(track.getAlbum().getGenre()));
        toReturn.setPlaylist(PlaylistMapper.mapToShallowDTO(track.getPlaylist()));
        return toReturn;
    }

    public static TrackDTO mapToShallowDTO(Track track) {
        TrackDTO toReturn = new TrackDTO();

        toReturn.setId(track.getId());
        toReturn.setName(track.getName());
        toReturn.setDuration(track.getDuration());
        toReturn.setLyrics(track.getLyrics());
        return toReturn;
    }
}
