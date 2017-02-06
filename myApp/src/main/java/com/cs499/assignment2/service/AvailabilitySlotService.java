package com.cs499.assignment2.service;

import com.cs499.assignment2.domain.AvailabilitySlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing AvailabilitySlot.
 */
public interface AvailabilitySlotService {

    /**
     * Save a availabilitySlot.
     *
     * @param availabilitySlot the entity to save
     * @return the persisted entity
     */
    AvailabilitySlot save(AvailabilitySlot availabilitySlot);

    /**
     *  Get all the availabilitySlots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvailabilitySlot> findAll(Pageable pageable);

    /**
     *  Get the "id" availabilitySlot.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvailabilitySlot findOne(Long id);

    /**
     *  Delete the "id" availabilitySlot.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
