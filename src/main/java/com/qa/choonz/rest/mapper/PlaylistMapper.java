package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;

import java.util.stream.Collectors;

public class PlaylistMapper {
    private TrackMapper trackMapper;

    public PlaylistDTO mapToDeepDTO(Playlist playlist) {
        //if mappers have not been initialised yet, initialise them
        if (trackMapper == null) {
            trackMapper = new TrackMapper();
        }

        PlaylistDTO toReturn = new PlaylistDTO();

        toReturn.setId(playlist.getId());
        toReturn.setName(playlist.getName());
        toReturn.setDescription(playlist.getDescription());
        toReturn.setArtwork(playlist.getArtwork());
        toReturn.setTracks(playlist.getTracks().stream().map(trackMapper::mapToShallowDTO).collect(Collectors.toList()));
        return toReturn;
    }

    public PlaylistDTO mapToShallowDTO(Playlist playlist) {
        PlaylistDTO toReturn = new PlaylistDTO();

        toReturn.setId(playlist.getId());
        toReturn.setName(playlist.getName());
        toReturn.setDescription(playlist.getDescription());
        toReturn.setArtwork(playlist.getArtwork());
        return toReturn;
    }
}
