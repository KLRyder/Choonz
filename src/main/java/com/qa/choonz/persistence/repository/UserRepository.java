package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    boolean existsByUsername(@NotNull String username);
    UserDetails findByUsername(@NotNull String username);
}
