package com.mytest.maersk.controller;

import com.mytest.maersk.model.Booking;
import com.mytest.maersk.model.BookingSequence;
import com.mytest.maersk.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Integer> createBooking(@Valid @RequestBody Booking booking) {
        HashMap<String, Integer> result = new HashMap<>();
        Integer id = bookingService.createBooking(booking);
        result.put("bookingRef", id);
        return result;
    }

    @GetMapping("/api/bookings/viewBookings")
    public Flux<Booking> findAll() {
        return bookingService.findAll();
    }

    @DeleteMapping("/api/bookings/delete")
    public void delete(@Valid @RequestBody Booking booking){
        bookingService.delete(booking);
    }

}
