package com.qa.choonz.rest.dto;

import com.qa.choonz.persistence.roles.UserRole;

public class UserDetailsDTO {
    private String username;
    private UserRole role;

    public UserDetailsDTO(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsDTO)) return false;

        UserDetailsDTO that = (UserDetailsDTO) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
