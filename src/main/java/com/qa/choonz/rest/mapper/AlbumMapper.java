package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

        List<Genre> toMap = new ArrayList<>();
        for (var track :album.getTracks()) {
            if (!toMap.contains(track.getGenre())){
                toMap.add(track.getGenre());
            }
        }
        List<GenreDTO> genres = toMap.stream().map(genreMapper::mapToShallowDTO).collect(Collectors.toList());

        toReturn.setId(album.getId());
        toReturn.setName(album.getName());
        toReturn.setCover(album.getCover());
        toReturn.setTracks(album.getTracks().stream().map(trackMapper::mapToDeepDTO).collect(Collectors.toList()));
        toReturn.setArtist(artistMapper.mapToShallowDTO(album.getArtist()));
        toReturn.setGenres(genres);
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
