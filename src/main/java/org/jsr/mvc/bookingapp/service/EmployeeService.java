package org.jsr.mvc.bookingapp.service;

import org.jsr.mvc.bookingapp.dto.request.EmployeeRequest;
import org.jsr.mvc.bookingapp.dto.response.EmployeeResponse;
import org.jsr.mvc.bookingapp.entity.Employee;
import org.jsr.mvc.bookingapp.entity.User;
import org.jsr.mvc.bookingapp.repo.EmployeeRepository;
import org.jsr.mvc.bookingapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        User user = userRepository.findById(employeeRequest.userId()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

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

    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Рабочий не найден"));

        return new EmployeeResponse(
                employee.getId(),
                employee.getUser().getEmail(),
                employee.getSpecialization()
        );

    }

    public List<EmployeeResponse> findAll() {

        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeResponse> responseList = new ArrayList<>();

        for (Employee employee : employees) {

            EmployeeResponse response = new EmployeeResponse(
                    employee.getId(),
                    employee.getUser().getEmail(),
                    employee.getSpecialization()
            );

            responseList.add(response);
        }

        return responseList;
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Id не найден"));

        employeeRepository.delete(employee);
    }
}
