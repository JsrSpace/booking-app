package org.jsr.mvc.bookingapp.dto.response;

import java.math.BigDecimal;

public record BookingServiceResponse(
        Long id,
        String name,
        Long durationMinutes,
        BigDecimal price
) {
}
