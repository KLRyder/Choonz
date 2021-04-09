package com.qa.choonz.rest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.AlbumDTO;
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
        List<Album> toMap = new ArrayList<>();
        for (Track track : genre.getTracks()) {
            var album = track.getAlbum();
            if (!toMap.contains(album)){
                toMap.add(album);
            }
        }
        List<AlbumDTO> albums = toMap.stream().map(albumMapper::mapToShallowDTO).collect(Collectors.toList());

        toReturn.setDescription(genre.getDescription());
        toReturn.setId(genre.getId());
        toReturn.setName(genre.getName());
        toReturn.setAlbums(albums);
        toReturn.setTracks(genre.getTracks().stream().map(trackMapper::mapToShallowDTO).collect(Collectors.toList()));
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
