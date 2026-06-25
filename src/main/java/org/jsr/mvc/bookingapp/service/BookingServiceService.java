package org.jsr.mvc.bookingapp.service;

import org.jsr.mvc.bookingapp.dto.request.BookingServiceRequest;
import org.jsr.mvc.bookingapp.dto.response.BookingServiceResponse;
import org.jsr.mvc.bookingapp.entity.BookingService;
import org.jsr.mvc.bookingapp.repo.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceService {
    private final BookingRepository bookingRepository;

    public BookingServiceService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingServiceResponse create(BookingServiceRequest request) {
        BookingService bookingService = BookingService.builder()
                .name(request.name())
                .durationMinutes(request.durationMinutes())
                .price(request.price())
                .build();

        BookingService saved = bookingRepository.save(bookingService);

        return new BookingServiceResponse(
                saved.getId(),
                saved.getName(),
                saved.getDurationMinutes(),
                saved.getPrice()
        );
    }

    public BookingServiceResponse findById(Long id) {

        BookingService bookingService = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Id не нвйден"));

        return new BookingServiceResponse(
                bookingService.getId(),
                bookingService.getName(),
                bookingService.getDurationMinutes(),
                bookingService.getPrice()
        );
    }

    public List<BookingServiceResponse> findAll() {

        List<BookingService> bookingServices = bookingRepository.findAll();
        List<BookingServiceResponse> bookingServiceResponses = new ArrayList<>();

        for (BookingService bookingService : bookingServices) {

            BookingServiceResponse bookingServiceResponse = new BookingServiceResponse(
                    bookingService.getId(),
                    bookingService.getName(),
                    bookingService.getDurationMinutes(),
                    bookingService.getPrice()
            );

            bookingServiceResponses.add(bookingServiceResponse);
        }

        return bookingServiceResponses;
    }

    public void delete(Long id) {

        BookingService bookingService = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Id не нвйден"));

        bookingRepository.delete(bookingService);

    }
}
