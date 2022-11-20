package com.mytest.maersk.service;

import com.mytest.maersk.helper.ServiceHelper;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.repository.BookingRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingSequenceService sequenceService;

    @Autowired
    ServiceHelper serviceHelper;

    public boolean isBookingAvailable(Booking booking) {
        JSONObject jsonObject = (JSONObject) serviceHelper.callService("maersk-endpoints.endpoints.inquiry");
        int availableSpace = (Integer)jsonObject.get("availableSpace");
        return availableSpace != 0 && availableSpace >= booking.getQuantity();
    }

    public Integer createBooking(Booking booking) {
        Booking newBooking = new Booking(sequenceService.bookingSequenceNextVal(),
                booking.getContainerSize(),
                booking.getContainerType(),
                booking.getOrigin(),
                booking.getDestination(),
                booking.getQuantity(),
                booking.getTimestamp());
        bookingRepository.save(newBooking).subscribe();
        return Integer.valueOf(newBooking.getId().toString());
    }

    public Flux<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }
}
