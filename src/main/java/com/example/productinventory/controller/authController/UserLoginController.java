package com.example.productinventory.controller.authController;

import com.example.productinventory.dto.Login.LoginRequest;
import com.example.productinventory.dto.Login.LoginResponse;
import com.example.productinventory.dto.userRegisterDto.UserRegisterRequset;
import com.example.productinventory.dto.userRegisterDto.UserRegisterResponse;
import com.example.productinventory.security.AuthService;
import com.example.productinventory.security.LogoutService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {
    private final AuthService  authService;
    private final PasswordEncoder encoder;
    private final LogoutService logoutService;

    public UserLoginController(AuthService authService, PasswordEncoder encoder, LogoutService logoutService) {
        this.authService = authService;
        this.encoder = encoder;
        this.logoutService = logoutService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse>  register(@Valid @RequestBody UserRegisterRequset request){
        String encode = encoder.encode(request.getPassword());
        request.setPassword(encode);
        UserRegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginResponse(@RequestBody LoginRequest request){
        LoginResponse login = authService.login(request);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutResponse(@RequestHeader("Authorization") String authorization){
        System.out.println("from controller before getting header");
        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            return ResponseEntity.badRequest()
                    .body("Invalid Authorization header");
        }
        System.out.println("from controller after getting header");
        String token = authorization.substring(7);
        logoutService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
}
