package org.jsr.mvc.bookingapp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Duration;

public record BookingServiceRequest(
        @NotBlank
        String name,

        @Min(1)
        Long durationMinutes,

        @DecimalMin(value = "0.00", inclusive = false, message = "Price cannot be less than 0")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price
) {
}
