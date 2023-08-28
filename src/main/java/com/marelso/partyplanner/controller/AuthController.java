package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.AuthRequest;
import com.marelso.partyplanner.service.AccountService;
import com.marelso.partyplanner.service.EncryptionService;
import com.marelso.partyplanner.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AccountService accountsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) throws NoSuchAlgorithmException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        EncryptionService.encrypt(request.getPassword())
                )
        );

        var account = accountsService.loadUserByUsername(request.getUsername());
        var token = jwtService.generateToken(account);

        return ResponseEntity.ok(token);
    }
}