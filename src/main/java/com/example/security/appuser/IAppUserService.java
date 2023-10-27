package com.example.security.appuser;

import com.example.security.registration.RegistrationRequest;
import com.example.security.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IAppUserService {
    List<AppUser> getUsers();
    AppUser registerUser(RegistrationRequest request);
    Optional<AppUser> findByEmail(String email);

    void saveAppUserVerification(AppUser theAppUser, String verification);

    String validateToken(String theToken);

    AppUser findBywEmail(String user);
}
