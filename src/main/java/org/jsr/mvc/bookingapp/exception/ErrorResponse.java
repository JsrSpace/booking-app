package org.jsr.mvc.bookingapp.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime time
) {
}
