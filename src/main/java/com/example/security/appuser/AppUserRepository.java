package com.example.security.appuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    public AppUser findAppUserByEmail(String email);
}
