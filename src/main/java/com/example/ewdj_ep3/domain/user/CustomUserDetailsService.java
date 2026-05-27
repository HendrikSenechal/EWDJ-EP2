package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.role.Role;
import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userService.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                convertAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> convertAuthorities(
            Collection<Role> roles) {

        return roles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(
                                "ROLE_" + role.getName()))
                .toList();
    }
}