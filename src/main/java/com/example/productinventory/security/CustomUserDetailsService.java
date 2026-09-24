package com.example.productinventory.security;

import com.example.productinventory.entity.User;
import com.example.productinventory.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo repository;

    public CustomUserDetailsService(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        System.out.println(">>> loadUserByUsername called");
        System.out.println(">>> username = " + username);

        User user = repository.findByUsername(username).orElseThrow(() -> {
            System.out.println(">>> USER NOT FOUND: " + username);
            return new UsernameNotFoundException("User not found with is name");
        });
        System.out.println(">>> USER FOUND: " + user.getUsername());
        System.out.println(">>> ROLE: " + user.getRole());
        System.out.println(">>> ENABLED: " + user.getEnabled());
        return new CustomUserDetails(user);


    }
}

