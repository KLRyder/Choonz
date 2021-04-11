package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class SessionService {
    UserRepository repo;

    public SessionService(UserRepository repo) {
        this.repo = repo;
    }

    public String create(UserDetails details) {
        if (repo.existsByUsername(details.getUsername())){
            return "Failed: Username in use.";
        }
        try {
            details.hashPassword(10000);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA3-256 hashing failed when creating a new user");
            return "Failed: Server security error";
        }
        this.repo.save(details);
        return "Success";
    }

    public UserDetails authenticate(UserDetails details) {
        System.out.println(details.getUsername());
        if (!repo.existsByUsername(details.getUsername())){
            System.out.println("no user");
            return null;
        }
        try {
            UserDetails stored = repo.findByUsername(details.getUsername());
            details.hashPassword(10000);
            System.out.println(details.getPassword());
            System.out.println(stored.getPassword());
            return (details.getPassword().equals(stored.getPassword()))? stored : null;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA3-256 hashing when creating a new user");
            return null;
        }
    }
}
