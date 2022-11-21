package com.maersk.booking.controller;

import com.maersk.booking.model.Booking;
import com.maersk.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookingController {

    private BookingService bookingService;

    public BookingController() {}

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to Maersk Booking Portal";
    }

    @PostMapping("/api/bookings/isBookingAvailable")
    public Map<String, Boolean> isBookingAvailable(@Valid @RequestBody Booking booking) {
        Boolean available = bookingService.isBookingAvailable(booking);
        Map<String, Boolean> result = new HashMap<>();
        result.put("available", available);
        return result;
    }

    @PostMapping("/api/bookings/createBooking")
    public Map<String, Long> createBooking(@Valid @RequestBody Booking booking) {
        HashMap<String, Long> result = new HashMap<>();
        Long id = bookingService.createBooking(booking).block().getId();
        result.put("bookingRef", id);
        return result;
    }

    @GetMapping("/api/bookings/viewBookings")
    public Flux<Booking> findAll() {
        return bookingService.findAll();
    }

    @GetMapping("/api/bookings/findById/{id}")
    public Mono<Booking> findById(@PathVariable Long id) {return bookingService.findById(id); };

    @DeleteMapping("/api/bookings/delete")
    public Boolean delete(@Valid @RequestBody Booking booking){
        return bookingService.delete(booking);
    }

}
