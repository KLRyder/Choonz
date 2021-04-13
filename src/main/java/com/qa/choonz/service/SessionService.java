package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.UserDetails;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.persistence.roles.UserRole;
import com.qa.choonz.rest.dto.UserDetailsDTO;
import com.qa.choonz.utils.ActiveSessions;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class SessionService {
    UserRepository repo;
    private ActiveSessions sessions;

    public SessionService(UserRepository repo, ActiveSessions sessions) {
        this.sessions = sessions;
        this.repo = repo;
    }

    public String create(UserDetails details) {
        if (repo.existsByUsername(details.getUsername())) {
            return "Failed: Username in use.";
        }
        try {
            details.hashPassword(10000);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA3-256 hashing failed when creating a new user");
            return "Failed: Server security error";
        }
        details.setRole(UserRole.USER);
        this.repo.save(details);
        return "Success";
    }

    public String authenticate(UserDetails details) {
        if (!repo.existsByUsername(details.getUsername())) {
            System.out.println("no user");
            return null;
        }
        try {
            UserDetails stored = repo.findByUsername(details.getUsername());
            details.hashPassword(10000);
            if (details.getPassword().equals(stored.getPassword())) {
                return sessions.createSession(stored);
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA3-256 hashing when creating a new user");
            return null;
        }
    }

    public String logout(String sessID){
        return sessions.endSession(sessID);
    }

    public UserDetailsDTO userDetails(String sessID) {
        var session = sessions.getSession(sessID);
        return (session == null) ? null : new UserDetailsDTO(session.getUsername(), session.getRole());
    }
}
