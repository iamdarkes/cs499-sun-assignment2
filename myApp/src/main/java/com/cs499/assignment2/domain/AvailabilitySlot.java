package com.cs499.assignment2.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.cs499.assignment2.domain.enumeration.AvailabilityReason;

/**
 * A AvailabilitySlot.
 */
@Entity
@Table(name = "availability_slot")
public class AvailabilitySlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 50)
    @Column(name = "description", length = 50)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_reason", nullable = false)
    private AvailabilityReason availabilityReason;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "to_date")
    private ZonedDateTime toDate;

    @ManyToOne
    private ParkingSpace parkingSpace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public AvailabilitySlot description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AvailabilityReason getAvailabilityReason() {
        return availabilityReason;
    }

    public AvailabilitySlot availabilityReason(AvailabilityReason availabilityReason) {
        this.availabilityReason = availabilityReason;
        return this;
    }

    public void setAvailabilityReason(AvailabilityReason availabilityReason) {
        this.availabilityReason = availabilityReason;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public AvailabilitySlot fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }

    public AvailabilitySlot toDate(ZonedDateTime toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public AvailabilitySlot parkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        return this;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvailabilitySlot availabilitySlot = (AvailabilitySlot) o;
        if (availabilitySlot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, availabilitySlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AvailabilitySlot{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", availabilityReason='" + availabilityReason + "'" +
            ", fromDate='" + fromDate + "'" +
            ", toDate='" + toDate + "'" +
            '}';
    }
}
