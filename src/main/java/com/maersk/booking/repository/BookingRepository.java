package com.maersk.booking.repository;

import com.maersk.booking.model.Booking;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Repository;

@EnableCassandraRepositories
@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, Long> {
}
