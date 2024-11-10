package com.ecommerce.magnik.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

}