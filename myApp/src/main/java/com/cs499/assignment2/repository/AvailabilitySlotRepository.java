package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.AvailabilitySlot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AvailabilitySlot entity.
 */
@SuppressWarnings("unused")
public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot,Long> {

}
