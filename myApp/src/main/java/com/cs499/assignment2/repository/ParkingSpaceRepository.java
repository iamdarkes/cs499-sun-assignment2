package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.ParkingSpace;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParkingSpace entity.
 */
@SuppressWarnings("unused")
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace,Long> {

}
