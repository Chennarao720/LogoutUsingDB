package com.example.productinventory.security;

import com.example.productinventory.entity.Role;
import com.example.productinventory.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

    @RequiredArgsConstructor
    public class CustomUserDetails implements UserDetails {
        private final User customer;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            List<GrantedAuthority> auth=new ArrayList<>();
            auth.add(new SimpleGrantedAuthority("ROLE_"+customer.getRole()));
            if(customer.getRole()== Role.ADMIN){
                auth.add(new SimpleGrantedAuthority("READ_ACCOUNT"));
            }
            return auth;
        }

        @Override
        public String getPassword() {
            return customer.getPassword();
        }

        @Override
        public String getUsername() {
            return customer.getUsername();
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
            return customer.getEnabled();
        }

        public String getEmail(){
            return customer.getEmail();
        }
        public Long customerId(){
            return customer.getId();
        }


}
