package org.jsr.mvc.bookingapp.service;

import org.jspecify.annotations.NonNull;
import org.jsr.mvc.bookingapp.dto.request.BookingServiceRequest;
import org.jsr.mvc.bookingapp.dto.response.BookingServiceResponse;
import org.jsr.mvc.bookingapp.entity.BookingService;
import org.jsr.mvc.bookingapp.exception.ResourceNotFoundException;
import org.jsr.mvc.bookingapp.repo.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceService {
    private final BookingRepository bookingRepository;

    public ServiceService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingServiceResponse create(BookingServiceRequest request) {
        BookingService bookingService = BookingService.builder()
                .name(request.name())
                .durationMinutes(request.durationMinutes())
                .price(request.price())
                .build();

        BookingService saved = bookingRepository.save(bookingService);

        return mapToResponse(saved);
    }

    public BookingServiceResponse findById(Long id) {

        BookingService bookingService = bookingRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Сервис с id: " + id + " не найден"
                ));

        return mapToResponse(bookingService);
    }

    public List<BookingServiceResponse> findAll() {

        List<BookingService> bookingServices = bookingRepository.findAll();
        List<BookingServiceResponse> bookingServiceResponses = new ArrayList<>();

        for (BookingService bookingService : bookingServices) {

            BookingServiceResponse bookingServiceResponse = mapToResponse(bookingService);

            bookingServiceResponses.add(bookingServiceResponse);
        }

        return bookingServiceResponses;
    }

    public void deleteById(Long id) {

        BookingService bookingService = bookingRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Севис уже удален либо не найден"
                ));

        bookingRepository.delete(bookingService);

    }

    public BookingServiceResponse updateService(Long id, BookingServiceRequest serviceRequest) {

        BookingService service = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сервис не найден с id: " + id));

        service.setName(serviceRequest.name());
        service.setDurationMinutes(serviceRequest.durationMinutes());
        service.setPrice(serviceRequest.price());

        BookingService updatedService = bookingRepository.save(service);

        return mapToResponse(updatedService);
    }

    private BookingServiceResponse mapToResponse(BookingService updatedService) {
        return new BookingServiceResponse(
                updatedService.getId(),
                updatedService.getName(),
                updatedService.getDurationMinutes(),
                updatedService.getPrice()
        );
    }
}
