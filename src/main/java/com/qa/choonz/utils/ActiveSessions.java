package com.qa.choonz.utils;

import com.qa.choonz.persistence.domain.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class ActiveSessions {
    private HashMap<UUID,UserDetails> sessions;

    public ActiveSessions() {
        sessions = new HashMap<>();
    }

    public UserDetails getSession(String sessionID){
        return sessions.getOrDefault(UUID.fromString(sessionID), null);
    }

    public String createSession(UserDetails user){
        UUID key = UUID.randomUUID();
        sessions.put(key, user);
        return key.toString();
    }

    public String endSession(String sessID){
        sessions.remove(UUID.fromString(sessID));
        return "none";
    }
}
