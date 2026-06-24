package org.jsr.mvc.bookingapp.repo;

import org.jsr.mvc.bookingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
