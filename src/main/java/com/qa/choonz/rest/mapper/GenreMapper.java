package com.qa.choonz.rest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.TrackDTO;

@Component
public class GenreMapper {
    private TrackMapper trackMapper;
    private AlbumMapper albumMapper;

    public GenreDTO mapToDeepDTO(Genre genre) {
        //if mappers have not been initialised yet, initialise them
        if (trackMapper == null || albumMapper == null){
            trackMapper = new TrackMapper();
            albumMapper = new AlbumMapper();
        }

        GenreDTO toReturn = new GenreDTO();
        List<TrackDTO> tracks = new ArrayList<>();

        for (Album album : genre.getAlbums()) {
            tracks.addAll(album.getTracks().stream().map(trackMapper::mapToShallowDTO).collect(Collectors.toList()));
        }

        toReturn.setDescription(genre.getDescription());
        toReturn.setId(genre.getId());
        toReturn.setName(genre.getName());
        toReturn.setAlbums(genre.getAlbums().stream().map(albumMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setTracks(tracks);
        return toReturn;
    }

    public GenreDTO mapToShallowDTO(Genre genre) {
        GenreDTO toReturn = new GenreDTO();

        toReturn.setName(genre.getName());
        toReturn.setId(genre.getId());
        toReturn.setDescription(genre.getDescription());
        return null;
    }
}
