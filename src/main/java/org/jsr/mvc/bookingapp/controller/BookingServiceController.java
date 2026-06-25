package org.jsr.mvc.bookingapp.controller;
import org.jsr.mvc.bookingapp.dto.request.BookingServiceRequest;
import org.jsr.mvc.bookingapp.dto.response.BookingServiceResponse;
import org.jsr.mvc.bookingapp.service.BookingServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class BookingServiceController {

    private final BookingServiceService bookingServiceService;

    public BookingServiceController(BookingServiceService bookingServiceService) {
        this.bookingServiceService = bookingServiceService;
    }

    @PostMapping
    public BookingServiceResponse create(@RequestBody BookingServiceRequest request) {
        return bookingServiceService.create(request);
    }

    @GetMapping("/{id}")
    private BookingServiceResponse findById(@PathVariable Long id) {
        return bookingServiceService.findById(id);
    }

    @GetMapping
    private List<BookingServiceResponse> findAll() {
        return bookingServiceService.findAll();
    }

    @DeleteMapping("/{is}")
    public void delete(@PathVariable Long id) {
        bookingServiceService.delete(id);
    }
}
