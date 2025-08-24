package com.demo.hms.controllers;

import com.demo.hms.dto.AuthRequest;
import com.demo.hms.dto.AuthenticatedUser;
import com.demo.hms.entity.User;
import com.demo.hms.exceptions.ResourceNotFoundException;
import com.demo.hms.repository.UserRepository;
import com.demo.hms.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUser> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
        optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        String jwtToken = jwtUtil.generateToken(authRequest.getUsername());
        User user = optionalUser.get();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(user.getId().toString())
                .email(user.getUsername())
                .role(user.getRole())
                .jwtToken(jwtToken).build();
        return ResponseEntity.ok(authenticatedUser);

    }
}
