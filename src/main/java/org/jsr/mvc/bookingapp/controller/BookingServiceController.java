package org.jsr.mvc.bookingapp.controller;
import jakarta.validation.Valid;
import org.jsr.mvc.bookingapp.dto.request.BookingServiceRequest;
import org.jsr.mvc.bookingapp.dto.response.BookingServiceResponse;
import org.jsr.mvc.bookingapp.service.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class BookingServiceController {

    private final ServiceService bookingServiceService;

    public BookingServiceController(ServiceService bookingServiceService) {
        this.bookingServiceService = bookingServiceService;
    }

    @PostMapping
    public BookingServiceResponse create(@RequestBody BookingServiceRequest request) {
        return bookingServiceService.create(request);
    }

    @GetMapping("/{id}")
    public BookingServiceResponse findById(@PathVariable Long id) {
        return bookingServiceService.findById(id);
    }

    @GetMapping
    public List<BookingServiceResponse> findAll() {
        return bookingServiceService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingServiceService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookingServiceResponse updateServiceById(@PathVariable Long id, @Valid @RequestBody BookingServiceRequest bookingServiceRequest) {
        return bookingServiceService.updateService(id, bookingServiceRequest);
    }
}
