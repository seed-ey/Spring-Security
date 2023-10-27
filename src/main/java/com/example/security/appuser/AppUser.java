//package com.example.security.appuser;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.NaturalId;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
//import java.util.Collection;
//import java.util.Collections;
//
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Data
//public class AppUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String firstName;
//    private String lastName;
//    @NaturalId(mutable = true)
//    private String email;
//    private String password;
//    private String role;
//    private Boolean locked;
//    private Boolean isEnabled = false;
//    }


//    public boolean isEnabled() {
//    }
//}
package com.example.security.appuser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String role;
    private Boolean locked;
    private boolean isEnabled = false;

}
