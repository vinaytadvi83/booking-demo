package com.mytest.maersk.model;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.COUNTER;

@Table
public class BookingSequence {

    @PrimaryKey
    private String name;

    public BookingSequence(Long nextVal) {
    this.nextVal = nextVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @CassandraType(type=COUNTER)
    private Long nextVal;

    public Long getNextVal() {
        return nextVal;
    }

    public void setNextVal(Long nextVal) {
        this.nextVal = nextVal;
    }
}
