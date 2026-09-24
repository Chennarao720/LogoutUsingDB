package com.example.productinventory.dto.userRegisterDto;

import com.example.productinventory.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterResponse {

    private Long id;
    private String username;
    private Role role;
    private Boolean enabled;
    private String email;
    private String fullname;
}
