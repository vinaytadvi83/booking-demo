package com.mytest.maersk.model;

import com.mytest.maersk.validators.NumberFrom;
import com.mytest.maersk.validators.ValidDateTimeFormat;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Size;

@Table
public class Booking {

    @PrimaryKey
    private Long id;

    @NumberFrom
    private Integer containerSize;
    private ContainerType containerType;
    @Size(min = 5, max = 20)
    private String origin;
    @Size(min = 5, max = 20)
    private String destination;
    private Integer quantity;

    //@JsonFormat( shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ" )
    @ValidDateTimeFormat
    private String timestamp;

    public Booking(){}

    public Booking(Long id, Integer containerSize, ContainerType containerType, String origin, String destination, Integer quantity, String timestamp) {
        this.id = id;
        this.containerSize = containerSize;
        this.containerType = containerType;
        this.origin = origin;
        this.destination = destination;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(Integer containerSize) {
        this.containerSize = containerSize;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
