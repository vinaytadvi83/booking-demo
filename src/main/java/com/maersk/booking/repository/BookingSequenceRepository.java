package com.maersk.booking.repository;

import com.maersk.booking.model.BookingSequence;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

//import java.util.UUID;

@Repository
public interface BookingSequenceRepository extends ReactiveCassandraRepository<BookingSequence, String> {

    //Initialize the counter (if not already) when application starts.
    @Query("UPDATE BookingSequence SET nextVal = nextVal + 957000001 WHERE name='BookingSequence'")
    Mono<BookingSequence> initBookingSequence();

    //Get next value for new records
    @Query("UPDATE BookingSequence SET nextVal = nextVal + 1 WHERE name='BookingSequence'")
    Mono<BookingSequence> updateBookingSequence();
}
