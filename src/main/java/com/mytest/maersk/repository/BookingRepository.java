package com.mytest.maersk.repository;

import com.mytest.maersk.model.Booking;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@EnableCassandraRepositories
@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, Long> {
}
