package com.qa.choonz.service;

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.PlaylistLink;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.PlaylistLinkRepository;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.mapper.PlaylistMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private PlaylistRepository repo;
    private PlaylistLinkRepository linkRepo;
    private PlaylistMapper mapper;

    public PlaylistService(PlaylistRepository repo, PlaylistMapper mapper, PlaylistLinkRepository linkRepo) {
        super();
        this.repo = repo;
        this.mapper = mapper;
        this.linkRepo = linkRepo;
    }

    public PlaylistDTO create(Playlist playlist, UserDetails user) {
        // ensure only logged in users can create playlist
        if (user == null) {
            return null;
        }
        Playlist created = this.repo.save(playlist);
        return mapper.mapToDeepDTO(created);
    }

    public List<PlaylistDTO> read() {
        return this.repo.findAll().stream().map(mapper::mapToDeepDTO).collect(Collectors.toList());
    }

    public PlaylistDTO read(long id) {
        Playlist found = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        return mapper.mapToDeepDTO(found);
    }

    public PlaylistDTO update(Playlist playlist, long id, UserDetails user) {
        Playlist toUpdate = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);

        // ensure only admin or the playlist creator can edit playlists
        if (user == null || (user != toUpdate.getCreator() && user.getRole() != UserRole.ADMIN)) {
            return null;
        }

        toUpdate.setName(playlist.getName());
        toUpdate.setDescription(playlist.getDescription());
        toUpdate.setArtwork(playlist.getArtwork());
        Playlist updated = this.repo.save(toUpdate);
        return mapper.mapToDeepDTO(updated);
    }

    public boolean delete(long id, UserDetails user) {
        Playlist toDelete = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);

        // ensure only admin or the playlist creator can edit playlists
        if (user == null || (user != toDelete.getCreator() && user.getRole() != UserRole.ADMIN)) {
            return false;
        }
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

    public boolean add(long id, long track, UserDetails user) {
        var tempPl = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        var tempTrack = new Track(track);
        if (!linkRepo.existsByPlaylistAndTrack(tempPl, tempTrack) &&
                user != null &&
                (tempPl.getCreator().equals(user) || user.getRole() == UserRole.ADMIN)) {
            linkRepo.save(new PlaylistLink(tempPl, tempTrack));
            return true;
        }
        return false;
    }

    @Transactional
    public boolean remove(long id, long track, UserDetails user) {
        var tempPl = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);

        if (tempPl.getCreator().equals(user) || user.getRole() == UserRole.ADMIN){
            var tempTrack = new Track(track);
            linkRepo.deleteByPlaylistAndTrack(tempPl, tempTrack);
            return !linkRepo.existsByPlaylistAndTrack(tempPl, tempTrack);
        }
        return false;
    }

    public Set<Playlist> read(String term) {
        return Set.copyOf(repo.findAllByNameContaining(term));
    }
}
