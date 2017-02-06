package com.cs499.assignment2.service.impl;

import com.cs499.assignment2.service.BookingSlotService;
import com.cs499.assignment2.domain.BookingSlot;
import com.cs499.assignment2.repository.BookingSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing BookingSlot.
 */
@Service
@Transactional
public class BookingSlotServiceImpl implements BookingSlotService{

    private final Logger log = LoggerFactory.getLogger(BookingSlotServiceImpl.class);
    
    private final BookingSlotRepository bookingSlotRepository;

    public BookingSlotServiceImpl(BookingSlotRepository bookingSlotRepository) {
        this.bookingSlotRepository = bookingSlotRepository;
    }

    /**
     * Save a bookingSlot.
     *
     * @param bookingSlot the entity to save
     * @return the persisted entity
     */
    @Override
    public BookingSlot save(BookingSlot bookingSlot) {
        log.debug("Request to save BookingSlot : {}", bookingSlot);
        BookingSlot result = bookingSlotRepository.save(bookingSlot);
        return result;
    }

    /**
     *  Get all the bookingSlots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookingSlot> findAll(Pageable pageable) {
        log.debug("Request to get all BookingSlots");
        Page<BookingSlot> result = bookingSlotRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bookingSlot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookingSlot findOne(Long id) {
        log.debug("Request to get BookingSlot : {}", id);
        BookingSlot bookingSlot = bookingSlotRepository.findOne(id);
        return bookingSlot;
    }

    /**
     *  Delete the  bookingSlot by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookingSlot : {}", id);
        bookingSlotRepository.delete(id);
    }
}
