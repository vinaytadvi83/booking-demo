package com.mytest.maersk.service;

import com.mytest.maersk.model.BookingSequence;
import com.mytest.maersk.repository.BookingRepository;
import com.mytest.maersk.repository.BookingSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BookingSequenceService {
    @Autowired
    private BookingSequenceRepository sequenceRepository;

    public Long bookingSequenceNextVal() {
        BookingSequence sequence = sequenceRepository.findById("BookingSequence").get();
        sequenceRepository.updateBookingSequence();
        return sequence.getNextVal();
    }

}
