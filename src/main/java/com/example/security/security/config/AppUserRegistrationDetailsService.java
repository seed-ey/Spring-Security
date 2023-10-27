package com.example.security.security.config;

import com.example.security.appuser.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserRegistrationDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .map(AppUserRegistrationDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
