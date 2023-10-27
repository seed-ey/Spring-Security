package com.example.security.appuser;

import com.example.security.exception.UserAlreadyExistsException;
import com.example.security.registration.RegistrationRequest;
import com.example.security.registration.token.VerificationToken;
import com.example.security.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppUserService implements  IAppUserService{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private Logger log = LoggerFactory.getLogger(AppUserService.class);


    @Override
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser registerUser(RegistrationRequest request) {
        Optional<AppUser> appUser = this.findByEmail(request.email());
        if (appUser.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email"+request.email() + "already exists!");
        }
        var newAppUser = new AppUser();
        newAppUser.setFirstName(request.firstname());
        newAppUser.setLastName(request.lastname());
        newAppUser.setEmail(request.email());
        newAppUser.setPassword(passwordEncoder.encode(request.password()));
        newAppUser.setRole(request.role());
        log.info("new user {} :", newAppUser);
        return appUserRepository.save(newAppUser);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public void saveAppUserVerification(AppUser theAppUser, String token) {
        var verificationToken = new VerificationToken(token, theAppUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        AppUser appUser = token.getAppUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        return "valid";
    }

    @Override
    public AppUser findBywEmail(String user) {
        return appUserRepository.findAppUserByEmail(user);
    }

    public String login (AppUser user){
        String email = String.valueOf(findByEmail(user.getEmail()));
        if(user.getEmail().equalsIgnoreCase(email)){

        }else {

        }
        return "/daas";
    }

}
