package com.qa.choonz.rest.mapper;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;

public class TrackMapper {
    private AlbumMapper albumMapper;
    private GenreMapper genreMapper;
    private ArtistMapper artistMapper;
    private PlaylistMapper playlistMapper;

    public TrackDTO mapToDeepDTO(Track track) {
        //if mappers have not been initialised yet, initialise them
        if (albumMapper == null || genreMapper == null ||
            artistMapper == null || playlistMapper == null){
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
        toReturn.setArtist((track.getAlbum() != null) ? artistMapper.mapToShallowDTO(track.getAlbum().getArtist()) : null);
        toReturn.setGenre((track.getAlbum() != null) ? genreMapper.mapToShallowDTO(track.getAlbum().getGenre()) : null);
        toReturn.setPlaylist(playlistMapper.mapToShallowDTO(track.getPlaylist()));
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
