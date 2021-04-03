package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistMapper {
    public static ArtistDTO mapToDeepDTO(Artist artist) {
        ArtistDTO toReturn = new ArtistDTO();
        List<TrackDTO> tracks = new ArrayList<>();

        for (Album album : artist.getAlbums()) {
            tracks.addAll(album.getTracks().stream().map(TrackMapper::mapToShallowDTO).collect(Collectors.toList()));
        }

        toReturn.setAlbums(artist.getAlbums().stream().map(AlbumMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setTracks(tracks);
        toReturn.setId(artist.getId());
        toReturn.setName(artist.getName());
        return toReturn;
    }

    public static ArtistDTO mapToShallowDTO(Artist artist) {
        ArtistDTO toReturn = new ArtistDTO();

        toReturn.setId(artist.getId());
        toReturn.setName(artist.getName());
        return toReturn;
    }
}
