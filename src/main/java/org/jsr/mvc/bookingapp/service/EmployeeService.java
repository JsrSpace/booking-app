package org.jsr.mvc.bookingapp.service;

import org.jsr.mvc.bookingapp.dto.request.EmployeeRequest;
import org.jsr.mvc.bookingapp.dto.response.EmployeeResponse;
import org.jsr.mvc.bookingapp.entity.Employee;
import org.jsr.mvc.bookingapp.entity.User;
import org.jsr.mvc.bookingapp.repo.EmployeeRepository;
import org.jsr.mvc.bookingapp.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        User user = userRepository.findById(employeeRequest.userId()).orElseThrow();

        Employee employee = Employee.builder()
                .user(user)
                .specialization(employeeRequest.specialization())
                .build();

        Employee saved = employeeRepository.save(employee);

        return new EmployeeResponse(
                saved.getId(),
                saved.getUser().getEmail(),
                saved.getSpecialization()
        );

    }
}
