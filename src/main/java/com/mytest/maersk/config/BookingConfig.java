package com.mytest.maersk.config;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.model.BookingSequence;
import com.mytest.maersk.repository.BookingSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BookingConfig implements ApplicationRunner {

    @Autowired
    private BookingSequenceRepository sequenceRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(sequenceRepository.findById("BookingSequence").isEmpty()) {
            sequenceRepository.initBookingSequence();
        }
    }
}