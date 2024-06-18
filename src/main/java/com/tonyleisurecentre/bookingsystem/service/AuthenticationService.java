package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.enums.Role;
import com.tonyleisurecentre.bookingsystem.exception.UserAlreadyExistedException;
import com.tonyleisurecentre.bookingsystem.models.User;
import com.tonyleisurecentre.bookingsystem.repository.UserRepository;
import com.tonyleisurecentre.bookingsystem.request.AuthenticationRequest;
import com.tonyleisurecentre.bookingsystem.request.RegisterRequest;
import com.tonyleisurecentre.bookingsystem.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistedException {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistedException("User already existed");
        }
        var user = new User();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
