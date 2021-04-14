package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TrackMapper {
    private AlbumMapper albumMapper;
    private GenreMapper genreMapper;
    private ArtistMapper artistMapper;
    private PlaylistMapper playlistMapper;

    public TrackDTO mapToDeepDTO(Track track) {
        //if mappers have not been initialised yet, initialise them
        if (albumMapper == null || genreMapper == null ||
                artistMapper == null || playlistMapper == null) {
            artistMapper = new ArtistMapper();
            albumMapper = new AlbumMapper();
            genreMapper = new GenreMapper();
            playlistMapper = new PlaylistMapper();
        }


        TrackDTO toReturn = new TrackDTO();

        toReturn.setId(track.getId());
        toReturn.setName(track.getName());
        toReturn.setDuration(track.getDuration());
        toReturn.setLyrics(track.getLyrics());
        toReturn.setAlbum(albumMapper.mapToShallowDTO(track.getAlbum()));
        toReturn.setArtists((track.getAlbum() == null || track.getAlbum().getArtists().size() == 0)
                ? null
                : track.getAlbum().getArtists().stream().map(link -> artistMapper.mapToShallowDTO(link.getArtist())).collect(Collectors.toList()));
        toReturn.setGenre(genreMapper.mapToShallowDTO(track.getGenre()));
//        toReturn.setPlaylist(playlistMapper.mapToShallowDTO(track.getPlaylist()));
        return toReturn;
    }

    public TrackDTO mapToShallowDTO(Track track) {
        TrackDTO toReturn = new TrackDTO();

        toReturn.setId(track.getId());
        toReturn.setName(track.getName());
        toReturn.setDuration(track.getDuration());
        toReturn.setLyrics(track.getLyrics());
        return toReturn;
    }
}
