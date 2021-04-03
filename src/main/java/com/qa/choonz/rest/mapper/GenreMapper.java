package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenreMapper {
    public static GenreDTO mapToDeepDTO(Genre genre) {
        GenreDTO toReturn = new GenreDTO();
        List<TrackDTO> tracks = new ArrayList<>();

        for (Album album : genre.getAlbums()) {
            tracks.addAll(album.getTracks().stream().map(TrackMapper::mapToShallowDTO).collect(Collectors.toList()));
        }

        toReturn.setAlbums(genre.getAlbums().stream().map(AlbumMapper::mapToShallowDTO).collect(Collectors.toList()));
        toReturn.setTracks(tracks);
        toReturn.setDescription(genre.getDescription());
        toReturn.setId(genre.getId());
        toReturn.setName(genre.getName());
        return toReturn;
    }

    public static GenreDTO mapToShallowDTO(Genre genre) {
        GenreDTO toReturn = new GenreDTO();

        toReturn.setName(genre.getName());
        toReturn.setId(genre.getId());
        toReturn.setDescription(genre.getDescription());
        return null;
    }
}
