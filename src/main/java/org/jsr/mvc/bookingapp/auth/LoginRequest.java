package org.jsr.mvc.bookingapp.auth;

public record LoginRequest(
        String email,
        String password
) {
}