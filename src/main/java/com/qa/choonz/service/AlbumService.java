package com.qa.choonz.service;

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.ArtistAlbumLink;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.repository.ArtistAlbumLinkRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.mapper.AlbumMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private AlbumRepository repo;
    private AlbumMapper mapper;
    private ArtistAlbumLinkRepository linkRepo;

    public AlbumService(AlbumRepository repo, AlbumMapper mapper, ArtistAlbumLinkRepository linkRepo) {
        super();
        this.repo = repo;
        this.mapper = mapper;
        this.linkRepo = linkRepo;
    }

    public AlbumDTO create(Album album, UserDetails user) {
        // ensure only admin can create albums
        if (user == null || user.getRole() != UserRole.ADMIN){return null;}

        Album created = this.repo.save(album);
        return mapper.mapToDeepDTO(created);
    }

    public List<AlbumDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public AlbumDTO read(long id) {
        Album found = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public AlbumDTO update(Album album, long id, UserDetails user) {
        // ensure only admin can edit albums
        if (user == null || user.getRole() != UserRole.ADMIN){return null;}

        Album toUpdate = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
        toUpdate.setName(album.getName());
        toUpdate.setCover(album.getCover());
        Album updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id, UserDetails user) {
        // ensure only admin can delete albums
        if (user == null || user.getRole() != UserRole.ADMIN){return false;}

        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

    public boolean add(long albumID, long artistID, UserDetails user) {
        var tempAlbum = new Album(albumID);
        var tempArtist = new Artist(artistID);
        if (!linkRepo.existsByArtistAndAlbum(tempArtist, tempAlbum) &&
                user != null && user.getRole() == UserRole.ADMIN) {
            linkRepo.save(new ArtistAlbumLink(tempArtist,tempAlbum));
            return true;
        }
        return false;
    }

    @Transactional
    public boolean remove(long albumId, long artistID, UserDetails user) {
        var tempAlbum = new Album(albumId);
        var tempArtist = new Artist(artistID);
        if (user != null && user.getRole() == UserRole.ADMIN){
            linkRepo.deleteByArtistAndAlbum(tempArtist, tempAlbum);
            return !linkRepo.existsByArtistAndAlbum(tempArtist, tempAlbum);
        }
        return false;
    }
}
