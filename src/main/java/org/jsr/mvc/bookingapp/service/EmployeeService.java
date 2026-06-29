package org.jsr.mvc.bookingapp.service;

import org.jspecify.annotations.NonNull;
import org.jsr.mvc.bookingapp.dto.request.EmployeeRequest;
import org.jsr.mvc.bookingapp.dto.response.EmployeeResponse;
import org.jsr.mvc.bookingapp.entity.Employee;
import org.jsr.mvc.bookingapp.entity.User;
import org.jsr.mvc.bookingapp.exception.ResourceNotFoundException;
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
        User user = userRepository.findById(employeeRequest.userId()).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Пользователь не найден")
                );

        Employee employee = Employee.builder()
                .user(user)
                .specialization(employeeRequest.specialization())
                .build();

        Employee saved = employeeRepository.save(employee);

        return mapToResponse(saved);

    }

    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Рабочий не найден")
                );

        return mapToResponse(employee);

    }

    public List<EmployeeResponse> findAll() {

        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeResponse> responseList = new ArrayList<>();

        for (Employee employee : employees) {

            EmployeeResponse response = mapToResponse(employee);

            responseList.add(response);
        }

        return responseList;
    }

    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Рабочий удален либо не найден"
                ));

        employeeRepository.delete(employee);
    }

    public EmployeeResponse updateById(Long id, EmployeeRequest employeeRequest) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Работник не найден с id: " + id));

        employee.setSpecialization(employeeRequest.specialization());

        if (employeeRequest.userId() != null) {
            User user = userRepository.findById(employeeRequest.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id : " + employeeRequest.userId()));
            employee.setUser(user);
        }

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getUser().getEmail(),
                employee.getSpecialization()
        );
    }
}
