package org.jsr.mvc.bookingapp.dto.request;

import org.jsr.mvc.bookingapp.entity.Status;

public record AppointmentStatusRequest(
        Status status
) {
}
