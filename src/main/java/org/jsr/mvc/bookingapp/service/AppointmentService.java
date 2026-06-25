package org.jsr.mvc.bookingapp.service;

import org.jsr.mvc.bookingapp.dto.request.AppointmentRequest;
import org.jsr.mvc.bookingapp.dto.response.AppointmentResponse;
import org.jsr.mvc.bookingapp.entity.*;
import org.jsr.mvc.bookingapp.repo.AppointmentRepository;
import org.jsr.mvc.bookingapp.repo.BookingRepository;
import org.jsr.mvc.bookingapp.repo.EmployeeRepository;
import org.jsr.mvc.bookingapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final BookingRepository bookingRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(UserRepository repository, UserRepository userRepository, EmployeeRepository employeeRepository, BookingRepository bookingRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.bookingRepository = bookingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentResponse create(AppointmentRequest appointmentRequest) {
        User user = userRepository.findById(appointmentRequest.customerId()).orElseThrow(() -> new RuntimeException("Id не найжен"));

        Employee employee = employeeRepository.findById(appointmentRequest.employeeId()).orElseThrow(() -> new RuntimeException("Id не найден"));

        BookingService bookingService = bookingRepository.findById(appointmentRequest.serviceId()).orElseThrow(() -> new RuntimeException("Id не найден"));

        LocalDateTime startTime = appointmentRequest.startTime();

        LocalDateTime endTime = startTime.plusMinutes(bookingService.getDurationMinutes());

        Boolean conflict = appointmentRepository.existsConflict(
                employee,
                startTime,
                endTime
        );

        if (conflict) {
            throw new RuntimeException("У рвбочего уже есть встреча");
        }

        Appointment appointment = Appointment.builder()
                .customer(user)
                .employee(employee)
                .service(bookingService)
                .startTime(appointmentRequest.startTime())
                .endTime(endTime)
                .status(Status.CREATED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        return new AppointmentResponse(
                saved.getId(),
                saved.getCustomer().getEmail(),
                saved.getEmployee().getUser().getEmail(),
                saved.getService().getName(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getStatus()
        );

    }

    public List<AppointmentResponse> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();

        List<AppointmentResponse> appointmentResponses = new ArrayList<>();

        for (Appointment appointment : appointments) {

            AppointmentResponse appointmentResponse = new AppointmentResponse(
                    appointment.getId(),
                    appointment.getCustomer().getEmail(),
                    appointment.getEmployee().getUser().getEmail(),
                    appointment.getService().getName(),
                    appointment.getStartTime(),
                    appointment.getEndTime(),
                    appointment.getStatus()
            );

            appointmentResponses.add(appointmentResponse);
        }

        return appointmentResponses;

    }

    public AppointmentResponse findById(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getCustomer().getEmail(),
                appointment.getEmployee().getUser().getEmail(),
                appointment.getService().getName(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus()
        );
    }

    public void deleteById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        appointmentRepository.delete(appointment);
    }
}
