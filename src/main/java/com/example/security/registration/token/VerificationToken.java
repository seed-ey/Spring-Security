package com.example.security.registration.token;

import com.example.security.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 15;


    @OneToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    public VerificationToken(String token, AppUser appUser) {
        this.token = token;
        this.appUser = appUser;
        this.expirationTime = this.getTokenExpirationTime();
    }
    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();

    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
