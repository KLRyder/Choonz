package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;

import java.util.stream.Collectors;

public class PlaylistMapper {
    public static PlaylistDTO mapToDeepDTO(Playlist playlist) {
        PlaylistDTO toReturn = new PlaylistDTO();

        toReturn.setId(playlist.getId());
        toReturn.setName(playlist.getName());
        toReturn.setDescription(playlist.getDescription());
        toReturn.setArtwork(playlist.getArtwork());
        toReturn.setTracks(playlist.getTracks().stream().map(TrackMapper::mapToShallowDTO).collect(Collectors.toList()));
        return toReturn;
    }

    public static PlaylistDTO mapToShallowDTO(Playlist playlist) {
        PlaylistDTO toReturn = new PlaylistDTO();

        toReturn.setId(playlist.getId());
        toReturn.setName(playlist.getName());
        toReturn.setDescription(playlist.getDescription());
        toReturn.setArtwork(playlist.getArtwork());
        return toReturn;
    }
}
