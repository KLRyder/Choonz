package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistMapper {
    private TrackMapper trackMapper;
    private AlbumMapper albumMapper;

    public ArtistDTO mapToDeepDTO(Artist artist) {
        //if mappers have not been initialised yet, initialise them
        if (trackMapper == null || albumMapper == null) {
            trackMapper = new TrackMapper();
            albumMapper = new AlbumMapper();
        }

        ArtistDTO toReturn = new ArtistDTO();
        List<TrackDTO> tracks = new ArrayList<>();

        for (Album album : artist.getAlbums()) {
            tracks.addAll(album.getTracks().stream().map(trackMapper::mapToShallowDTO).collect(Collectors.toList()));
        }

        toReturn.setAlbums(artist.getAlbums().stream().map(albumMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setTracks(tracks);
        toReturn.setId(artist.getId());
        toReturn.setName(artist.getName());
        return toReturn;
    }

    public ArtistDTO mapToShallowDTO(Artist artist) {
        ArtistDTO toReturn = new ArtistDTO();

        toReturn.setId(artist.getId());
        toReturn.setName(artist.getName());
        return toReturn;
    }
}
