package com.tonyleisurecentre.bookingsystem.models;

import com.tonyleisurecentre.bookingsystem.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.mapstruct.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Column(name="password")
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).toList();
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
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
