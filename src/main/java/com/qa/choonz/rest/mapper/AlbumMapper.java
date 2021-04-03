package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;

import java.util.stream.Collectors;

public class AlbumMapper {
    public static AlbumDTO mapToDeepDTO(Album album) {
        AlbumDTO toReturn = new AlbumDTO();

        toReturn.setId(album.getId());
        toReturn.setName(album.getName());
        toReturn.setCover(album.getCover());
        toReturn.setTracks(album.getTracks().stream().map(TrackMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setArtist(ArtistMapper.mapToShallowDTO(album.getArtist()));
        toReturn.setGenre(GenreMapper.mapToShallowDTO(album.getGenre()));
        return toReturn;
    }

    public static AlbumDTO mapToShallowDTO(Album album) {
        AlbumDTO toReturn = new AlbumDTO();

        toReturn.setId(album.getId());
        toReturn.setName(album.getName());
        toReturn.setCover(album.getCover());
        return toReturn;
    }
}
