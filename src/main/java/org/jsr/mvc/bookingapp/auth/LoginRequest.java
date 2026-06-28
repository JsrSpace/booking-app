package org.jsr.mvc.bookingapp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "email должен быть валидным")
        @NotBlank(message = "email не может быть пустым")
        String email,
        String password
) {
}