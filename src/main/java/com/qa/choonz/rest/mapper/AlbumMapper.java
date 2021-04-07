package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AlbumMapper {
    private TrackMapper trackMapper;
    private ArtistMapper artistMapper;
    private GenreMapper genreMapper;

    public AlbumDTO mapToDeepDTO(Album album) {
        //if mappers have not been initialised yet, initialise them
        if (trackMapper == null || artistMapper == null || genreMapper == null) {
            trackMapper = new TrackMapper();
            artistMapper = new ArtistMapper();
            genreMapper = new GenreMapper();
        }

        AlbumDTO toReturn = new AlbumDTO();

        toReturn.setId(album.getId());
        toReturn.setName(album.getName());
        toReturn.setCover(album.getCover());
        toReturn.setTracks(album.getTracks().stream().map(trackMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setArtist(artistMapper.mapToShallowDTO(album.getArtist()));
        toReturn.setGenre(genreMapper.mapToShallowDTO(album.getGenre()));
        return toReturn;
    }

    public AlbumDTO mapToShallowDTO(Album album) {
        AlbumDTO toReturn = new AlbumDTO();

        toReturn.setId(album.getId());
        toReturn.setName(album.getName());
        toReturn.setCover(album.getCover());
        return toReturn;
    }
}
