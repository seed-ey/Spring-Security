package com.example.security.security.config;

import com.example.security.appuser.AppUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.config.http.MatcherType.regex;

@Data
public class AppUserRegistrationDetails implements UserDetails {

    private String userName;
    private  String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public AppUserRegistrationDetails(AppUser appUser) {
        this.userName = appUser.getEmail();
        this.password = appUser.getPassword();
        this.isEnabled = appUser.isEnabled();
        this.authorities = Arrays.stream(appUser.getRole()
                .split( ","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
