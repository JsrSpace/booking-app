package org.jsr.mvc.bookingapp.dto.response;

import org.jsr.mvc.bookingapp.entity.Status;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        String customerEmail,
        String employeeName,
        String serviceName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Status status
) {
}
