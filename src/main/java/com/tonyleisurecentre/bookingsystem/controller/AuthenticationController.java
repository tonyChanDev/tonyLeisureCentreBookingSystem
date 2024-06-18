package com.tonyleisurecentre.bookingsystem.controller;

import com.tonyleisurecentre.bookingsystem.exception.UserAlreadyExistedException;
import com.tonyleisurecentre.bookingsystem.request.AuthenticationRequest;
import com.tonyleisurecentre.bookingsystem.request.RegisterRequest;
import com.tonyleisurecentre.bookingsystem.response.AuthenticationResponse;
import com.tonyleisurecentre.bookingsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController (AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Auth API is available.";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserAlreadyExistedException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
