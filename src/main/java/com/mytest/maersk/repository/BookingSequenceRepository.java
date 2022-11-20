package com.mytest.maersk.repository;

import com.mytest.maersk.model.BookingSequence;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface BookingSequenceRepository extends CassandraRepository<BookingSequence, String> {

    //Initialize the counter (if not already) when application starts.
    @Query("UPDATE BookingSequence SET nextVal = nextVal + 957000001 WHERE name='BookingSequence'")
    public void initBookingSequence();

    //Get next value for new records
    @Query("UPDATE BookingSequence SET nextVal = nextVal + 1 WHERE name='BookingSequence'")
    public void updateBookingSequence();
}
