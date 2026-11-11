
package com.example.productinventory.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private boolean success;
    private String message;
    private LoginData data;

    @Getter
    @AllArgsConstructor
    public static class LoginData {

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private long expiresIn;
        private UserInfo user;
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfo {

        private Long id;
        private String username;
        private String email;
        private Collection<? extends GrantedAuthority> role;
    }
}
