package com.example.security.registration;

import com.example.security.appuser.AppUser;
import com.example.security.appuser.AppUserService;
import com.example.security.appuser.UserLogin;
import com.example.security.event.RegistrationCompleteEvent;
import com.example.security.registration.token.VerificationToken;
import com.example.security.registration.token.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1")
@AllArgsConstructor

public class RegistrationController {
    @Autowired
    private  AppUserService appUserService;
    @Autowired
    private  ApplicationEventPublisher publisher;
    @Autowired
    private  VerificationTokenRepository tokenRepository;

//    @Autowired
//    private AuthenticationManager authenticationManager;


//    @Autowired
//    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public  String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){

        AppUser appUser = appUserService.registerUser(registrationRequest);
//            publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(appUser, applicationUrl(request)));
        return "Success! Please, check your email to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getAppUser().isEnabled()){
            return  "This account has already been verified, please login!";
        }
        String verificationResult = appUserService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return  "Email verified successfully. Now you can login to your account";
        }
        return  "Invalid verification token";
    }

    @GetMapping("/getsallUsers")
    public List<AppUser> getAppUsers(){
        return appUserService.getUsers();
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

//    private String login(@RequestBody AppUser user){
//
//     return appUserService.login(user);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin loginE) {
        AppUser user = appUserService.findBywEmail(loginE.getEmail());
        System.out.println("email===   " + user);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        if (!passwordMatches(loginE.getPassword(), user.getPassword())) {
            // Handle the case where the password is incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
        }

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//        );

      //  SecurityContextHolder.getContext().setAuthentication(authentication);

       // String jwt = tokenProvider.generateToken(authentication);

        // You can customize the response based on your application's needs
        Map<String, String> response = new HashMap<>();
        response.put("token",user.getFirstName());

        return ResponseEntity.ok(response);
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

}
