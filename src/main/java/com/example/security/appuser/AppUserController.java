package com.example.security.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/getsallUsers")
    public List<AppUser> getAppUsers(){
        return appUserService.getUsers();
    }
}
