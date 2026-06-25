package org.jsr.mvc.bookingapp.dto.request;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long customerId,
        Long employeeId,
        Long serviceId,
        LocalDateTime startTime
) {
}
