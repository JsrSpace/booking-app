package org.jsr.mvc.bookingapp.repo;

import org.jsr.mvc.bookingapp.entity.Appointment;
import org.jsr.mvc.bookingapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
       SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
       FROM Appointment a
       WHERE a.employee = :employee
       AND a.startTime < :endTime
       AND a.endTime > :startTime
       """)
    Boolean existsConflict(
            Employee employee,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
