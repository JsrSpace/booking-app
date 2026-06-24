package org.jsr.mvc.bookingapp.controller;

import org.jsr.mvc.bookingapp.dto.request.EmployeeRequest;
import org.jsr.mvc.bookingapp.dto.response.EmployeeResponse;
import org.jsr.mvc.bookingapp.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }

    @GetMapping("/{id}")
    public EmployeeResponse findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping
    public List<EmployeeResponse> findAll() {
        return employeeService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
