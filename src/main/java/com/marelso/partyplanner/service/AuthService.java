package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.PermissionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountsService;
    private final JwtService jwtService;

    public void authorize(String token, PermissionType permission) {
        if(!getUser(token).getPermissionType().hasPermission(permission)) {
            throw new RuntimeException("Feature not allowed for this user.");
        }
    }

    private Account getUser(String token) {
        String username = jwtService.extractUserName(tokenHandler(token));

        return accountsService.loadUserByUsername(username);
    }

    private String tokenHandler(String token) {
        return token.substring(7);
    }
}