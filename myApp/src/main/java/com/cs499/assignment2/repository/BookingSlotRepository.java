package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.BookingSlot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BookingSlot entity.
 */
@SuppressWarnings("unused")
public interface BookingSlotRepository extends JpaRepository<BookingSlot,Long> {

}
