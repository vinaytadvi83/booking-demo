package com.mytest.maersk.service;

import com.mytest.maersk.helper.ServiceHelper;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.repository.BookingRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingSequenceService sequenceService;

    @Autowired
    ServiceHelper serviceHelper;

    //This method calls service helper, which make service call to "https://maersk.com/api/bookings/checkAvailable"
    //Which is not available and can't be used as of now.
    public boolean isBookingAvailable(Booking booking) {
        JSONObject jsonObject = (JSONObject) serviceHelper.callService("maersk-endpoints.endpoints.inquiry");
        int availableSpace = (Integer)jsonObject.get("availableSpace");
        return availableSpace != 0 && availableSpace >= booking.getQuantity();
    }

    public Mono<Booking> createBooking(Booking booking) {
        Booking newBooking = new Booking(sequenceService.bookingSequenceNextVal(),
                booking.getContainerSize(),
                booking.getContainerType(),
                booking.getOrigin(),
                booking.getDestination(),
                booking.getQuantity(),
                booking.getTimestamp());
        Mono<Booking> bookingMono = bookingRepository.save(newBooking);
        bookingMono.subscribe();
        return bookingMono;
    }

    public Flux<Booking> findAll() {
        return bookingRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Booking> findById(Long id) { return bookingRepository.findById(id); }

    public Boolean delete(Booking booking) {
        Mono<Booking> dbBooking = findById(booking.getId());
        if (Objects.isNull(dbBooking)) {
            return false;
        }
        bookingRepository.delete(dbBooking.block()).subscribe();
        return true;
    }
}
