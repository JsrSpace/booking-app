package org.jsr.mvc.bookingapp.auth;

import org.jsr.mvc.bookingapp.entity.Role;

public record RegisterRequest(
        String email,
        String password,
        Role role
) {
}