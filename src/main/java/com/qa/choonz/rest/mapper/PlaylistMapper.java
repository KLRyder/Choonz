package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
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
        toReturn.setTracks(playlist.getTracks().stream().map(link -> trackMapper.mapToShallowDTO(link.getTrack())).collect(Collectors.toList()));
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
