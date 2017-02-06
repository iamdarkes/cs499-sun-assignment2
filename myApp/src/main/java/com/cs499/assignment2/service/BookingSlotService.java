package com.cs499.assignment2.service;

import com.cs499.assignment2.domain.BookingSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing BookingSlot.
 */
public interface BookingSlotService {

    /**
     * Save a bookingSlot.
     *
     * @param bookingSlot the entity to save
     * @return the persisted entity
     */
    BookingSlot save(BookingSlot bookingSlot);

    /**
     *  Get all the bookingSlots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BookingSlot> findAll(Pageable pageable);

    /**
     *  Get the "id" bookingSlot.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BookingSlot findOne(Long id);

    /**
     *  Delete the "id" bookingSlot.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
