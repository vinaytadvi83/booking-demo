package com.maersk.booking.service;

import com.maersk.booking.model.BookingSequence;
import com.maersk.booking.repository.BookingSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingSequenceService {
    @Autowired
    private BookingSequenceRepository sequenceRepository;

    public Long bookingSequenceNextVal() {
        BookingSequence sequence = sequenceRepository.findById("BookingSequence").block();
        sequenceRepository.updateBookingSequence().subscribe();
        return sequence.getNextVal();
    }

}
