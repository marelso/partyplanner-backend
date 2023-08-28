package com.marelso.partyplanner.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "accounts")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private String lastName;
    private String profilePicture;
    private PermissionType permissionType;
    private boolean deleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }
    @Override
    public boolean isAccountNonExpired() {
        return !this.deleted;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !this.deleted;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return !this.deleted;
    }
    @Override
    public boolean isEnabled() {
        return !this.deleted;
    }
}