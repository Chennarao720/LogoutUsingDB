package com.example.productinventory.security;

import com.example.productinventory.dto.Login.LoginRequest;
import com.example.productinventory.dto.Login.LoginResponse;
import com.example.productinventory.dto.Login.RefreshTokenResponse;
import com.example.productinventory.dto.userRegisterDto.UserRegisterRequset;
import com.example.productinventory.dto.userRegisterDto.UserRegisterResponse;
import com.example.productinventory.entity.User;
import com.example.productinventory.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService service;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    public AuthService(AuthenticationManager authenticationManager, CustomUserDetailsService service, JwtService jwtService, UserRepo userRepo, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public UserRegisterResponse register(UserRegisterRequset registerRequset){
        User user = mapper.map(registerRequset, User.class);
        User save = userRepo.save(user);
        UserRegisterResponse register = mapper.map(save, UserRegisterResponse.class);
        return register;
    }

    public LoginResponse login(LoginRequest request) {
//        authentication request is created by UsernamePasswordAuthenticationToken
//        and  Passing it to AuthenticationManager
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (request.getUsername(),request.getPassword()));

        UserDetails userDetails =(UserDetails) authenticate.getPrincipal();

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authenticate.getPrincipal();

        //for access token
        String accessToken =
                jwtService.generateToken(userDetails);

        //for refreshToken
        String refreshToken= jwtService.generateRefreshToken(userDetails);

        LoginResponse.UserInfo userInfo=new LoginResponse.UserInfo(customUserDetails.customerId(),
                userDetails.getUsername(),
                customUserDetails.getEmail(),
                customUserDetails.getAuthorities());
        LoginResponse.LoginData data = new LoginResponse.LoginData(
                accessToken,
                refreshToken,
                "Bearer",
                900L, // Replace with your actual access-token lifetime in seconds
                userInfo
        );

        return new LoginResponse(true, "Login successful", data);
    }

    public RefreshTokenResponse refreshToken(String refreshtoken){
        String s = jwtService.extractUsername(refreshtoken);
        UserDetails userDetails = service.loadUserByUsername(s);

        if(jwtService.isTokenValid(refreshtoken,userDetails)){
            String newAccessToken = jwtService.generateToken(userDetails);
            return new RefreshTokenResponse(newAccessToken,refreshtoken);
        }
        else{
            throw new RuntimeException("Invalid refresh token");
        }
    }

}


