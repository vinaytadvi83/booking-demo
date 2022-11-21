package com.maersk.booking.config;

import com.maersk.booking.repository.BookingSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/*
 * Initializing Booking Sequence when application starts if not already done
 */

@Component
public class BookingApplicationConfig implements ApplicationRunner {

    @Autowired
    private BookingSequenceRepository sequenceRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(sequenceRepository.findById("BookingSequence").blockOptional().isEmpty()) {
            sequenceRepository.initBookingSequence().subscribe();
        }
    }
}