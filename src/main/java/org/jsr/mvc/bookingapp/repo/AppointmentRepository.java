package org.jsr.mvc.bookingapp.repo;

import org.jsr.mvc.bookingapp.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
