package com.example.productinventory.controller.authController;

import com.example.productinventory.dto.Login.LoginRequest;
import com.example.productinventory.dto.Login.LoginResponse;
import com.example.productinventory.dto.userRegisterDto.UserRegisterRequset;
import com.example.productinventory.dto.userRegisterDto.UserRegisterResponse;
import com.example.productinventory.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {
    private final AuthService  authService;
    private final PasswordEncoder encoder;

    public UserLoginController(AuthService authService, PasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse>  register(@RequestBody UserRegisterRequset request){
        String encode = encoder.encode(request.getPassword());
        request.setPassword(encode);
        UserRegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginResponseResponseEntity(@RequestBody LoginRequest request){
        LoginResponse login = authService.login(request);
        return ResponseEntity.ok(login);
    }
}
