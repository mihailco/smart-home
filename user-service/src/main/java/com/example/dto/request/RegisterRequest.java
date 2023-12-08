package com.example.dto.request;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RegisterRequest {
    @NotBlank
    String name;
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotBlank
    String confirmPassword;

    @AssertTrue(message = "password and  confirm password should be equals")
    private boolean isValid() {
        return Objects.equals(password, confirmPassword);
    }
}
