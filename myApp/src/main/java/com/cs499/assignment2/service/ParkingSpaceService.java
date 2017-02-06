package com.cs499.assignment2.service;

import com.cs499.assignment2.domain.ParkingSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ParkingSpace.
 */
public interface ParkingSpaceService {

    /**
     * Save a parkingSpace.
     *
     * @param parkingSpace the entity to save
     * @return the persisted entity
     */
    ParkingSpace save(ParkingSpace parkingSpace);

    /**
     *  Get all the parkingSpaces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ParkingSpace> findAll(Pageable pageable);

    /**
     *  Get the "id" parkingSpace.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ParkingSpace findOne(Long id);

    /**
     *  Delete the "id" parkingSpace.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
