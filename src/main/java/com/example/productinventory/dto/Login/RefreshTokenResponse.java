package com.example.productinventory.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponse {
    private String jwtToken;
    private String refreshToken;

}
