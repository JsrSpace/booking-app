package org.jsr.mvc.bookingapp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jsr.mvc.bookingapp.entity.Role;

public record RegisterRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password,

        @NotNull
        Role role
) {
}