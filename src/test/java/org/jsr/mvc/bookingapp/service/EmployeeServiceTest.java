package org.jsr.mvc.bookingapp.service;

import org.jsr.mvc.bookingapp.dto.request.EmployeeRequest;
import org.jsr.mvc.bookingapp.dto.response.EmployeeResponse;
import org.jsr.mvc.bookingapp.entity.Employee;
import org.jsr.mvc.bookingapp.entity.User;
import org.jsr.mvc.bookingapp.exception.ResourceNotFoundException;
import org.jsr.mvc.bookingapp.repo.EmployeeRepository;
import org.jsr.mvc.bookingapp.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void shouldReturnEmployeeById() {
        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.findById(1L);

        assertEquals("barber@gmail.com", response.email());
        assertEquals("Barber", response.specialization());

        verify(employeeRepository).findById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.findById(1L)
        );
    }

    @Test
    public void shouldCreateEmployeeWhenUserExists() {

        User user = User.builder()
                .id(1L)
                .email("barber@gmail.com")
                .build();

        EmployeeRequest request = new EmployeeRequest(
                1L,
                "Barber"
        );

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponse response = employeeService.create(request);

        assertEquals("barber@gmail.com", response.email());
        assertEquals("Barber", response.specialization());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFoundDuringCreation() {
        EmployeeRequest request = new EmployeeRequest(
                1L, "Barber"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.create(request));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void shouldReturnListOfEmployees() {

        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeResponse> responses = employeeService.findAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Barber", responses.get(0).specialization());
        verify(employeeRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyListWhenNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<EmployeeResponse> responses = employeeService.findAll();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    public void shouldDeleteEmployeeWhenExists() {

        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        assertDoesNotThrow(() -> employeeService.deleteById(1L));

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteById(1L));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    public void shouldUpdateSpecializationAndUserWhenBothProvided() {
        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();

        EmployeeRequest updateRequest = new EmployeeRequest(2L, "Top Barber");
        User newUser = User.builder().id(2L).email("new@gmail.com").build();

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .user(newUser)
                .specialization("Top Barber")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(userRepository.findById(2L)).thenReturn(Optional.of(newUser));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        EmployeeResponse response = employeeService.updateById(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Top Barber", response.specialization());
        assertEquals("new@gmail.com", response.email());
        verify(employeeRepository).save(employee);
    }

    @Test
    public void shouldUpdateOnlySpecializationWhenUserIdIsNull() {
        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();


        EmployeeRequest updateRequest = new EmployeeRequest(null, "Just Barber");

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Just Barber")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        EmployeeResponse response = employeeService.updateById(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Just Barber", response.specialization());
        assertEquals("barber@gmail.com", response.email());
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionWhenEmployeeNotFoundDuringUpdate() {
        EmployeeRequest updateRequest = new EmployeeRequest(1L, "Barber");
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateById(1L, updateRequest));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFoundDuringUpdate() {

        User user = User.builder()
                .email("barber@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .specialization("Barber")
                .build();

        EmployeeRequest updateRequest = new EmployeeRequest(2L, "Barber");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateById(1L, updateRequest));
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
