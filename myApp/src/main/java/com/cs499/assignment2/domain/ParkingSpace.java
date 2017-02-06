package com.cs499.assignment2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ParkingSpace.
 */
@Entity
@Table(name = "parking_space")
public class ParkingSpace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 5, max = 50)
    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "expiration")
    private LocalDate expiration;

    @OneToMany(mappedBy = "parkingSpace")
    @JsonIgnore
    private Set<AvailabilitySlot> availabilitySlots = new HashSet<>();

    @OneToMany(mappedBy = "parkingSpace")
    @JsonIgnore
    private Set<BookingSlot> bookingSlots = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ParkingSpace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ParkingSpace description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public ParkingSpace expiration(LocalDate expiration) {
        this.expiration = expiration;
        return this;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public Set<AvailabilitySlot> getAvailabilitySlots() {
        return availabilitySlots;
    }

    public ParkingSpace availabilitySlots(Set<AvailabilitySlot> availabilitySlots) {
        this.availabilitySlots = availabilitySlots;
        return this;
    }

    public ParkingSpace addAvailabilitySlots(AvailabilitySlot availabilitySlot) {
        this.availabilitySlots.add(availabilitySlot);
        availabilitySlot.setParkingSpace(this);
        return this;
    }

    public ParkingSpace removeAvailabilitySlots(AvailabilitySlot availabilitySlot) {
        this.availabilitySlots.remove(availabilitySlot);
        availabilitySlot.setParkingSpace(null);
        return this;
    }

    public void setAvailabilitySlots(Set<AvailabilitySlot> availabilitySlots) {
        this.availabilitySlots = availabilitySlots;
    }

    public Set<BookingSlot> getBookingSlots() {
        return bookingSlots;
    }

    public ParkingSpace bookingSlots(Set<BookingSlot> bookingSlots) {
        this.bookingSlots = bookingSlots;
        return this;
    }

    public ParkingSpace addBookingSlots(BookingSlot bookingSlot) {
        this.bookingSlots.add(bookingSlot);
        bookingSlot.setParkingSpace(this);
        return this;
    }

    public ParkingSpace removeBookingSlots(BookingSlot bookingSlot) {
        this.bookingSlots.remove(bookingSlot);
        bookingSlot.setParkingSpace(null);
        return this;
    }

    public void setBookingSlots(Set<BookingSlot> bookingSlots) {
        this.bookingSlots = bookingSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParkingSpace parkingSpace = (ParkingSpace) o;
        if (parkingSpace.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parkingSpace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", expiration='" + expiration + "'" +
            '}';
    }
}
