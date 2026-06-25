package org.jsr.mvc.bookingapp.repo;

import org.jsr.mvc.bookingapp.entity.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingService, Long> {
}
