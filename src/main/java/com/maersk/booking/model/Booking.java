package com.maersk.booking.model;

import com.maersk.booking.validator.NumberFrom;
import com.maersk.booking.validator.ValidDateTimeFormat;
import com.maersk.booking.validator.ContainerTypes;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table
public class Booking {

    @PrimaryKey
    private Long id;

    @NumberFrom(values = {20, 40}, message = "Container size can be 20 or 40!")
    private Integer containerSize;

    @ContainerTypes(anyOf = {ContainerType.DRY, ContainerType.REEFER}, message = "Containers types DRY or REEFRE allowed!")
    private ContainerType containerType;

    @NotBlank
    @Size(min = 5, max = 20, message = "Origin name should be between 5 and 20 characters!")
    private String origin;

    @NotBlank
    @Size(min = 5, max = 20, message = "Destination name should be between 5 and 20 characters!")
    private String destination;

    @Range(min=1, max=100, message = "The Booking quantity should be between 1 and 100!")
    private Integer quantity;

    @ValidDateTimeFormat(message = "Date format should be in ISO-8601 date and time for UTC timezone e.g. 2020-10-12T13:53:09Z")
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
