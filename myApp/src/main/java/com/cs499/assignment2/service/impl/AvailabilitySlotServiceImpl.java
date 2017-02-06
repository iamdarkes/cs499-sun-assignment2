package com.cs499.assignment2.service.impl;

import com.cs499.assignment2.service.AvailabilitySlotService;
import com.cs499.assignment2.domain.AvailabilitySlot;
import com.cs499.assignment2.repository.AvailabilitySlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing AvailabilitySlot.
 */
@Service
@Transactional
public class AvailabilitySlotServiceImpl implements AvailabilitySlotService{

    private final Logger log = LoggerFactory.getLogger(AvailabilitySlotServiceImpl.class);
    
    private final AvailabilitySlotRepository availabilitySlotRepository;

    public AvailabilitySlotServiceImpl(AvailabilitySlotRepository availabilitySlotRepository) {
        this.availabilitySlotRepository = availabilitySlotRepository;
    }

    /**
     * Save a availabilitySlot.
     *
     * @param availabilitySlot the entity to save
     * @return the persisted entity
     */
    @Override
    public AvailabilitySlot save(AvailabilitySlot availabilitySlot) {
        log.debug("Request to save AvailabilitySlot : {}", availabilitySlot);
        AvailabilitySlot result = availabilitySlotRepository.save(availabilitySlot);
        return result;
    }

    /**
     *  Get all the availabilitySlots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvailabilitySlot> findAll(Pageable pageable) {
        log.debug("Request to get all AvailabilitySlots");
        Page<AvailabilitySlot> result = availabilitySlotRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one availabilitySlot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AvailabilitySlot findOne(Long id) {
        log.debug("Request to get AvailabilitySlot : {}", id);
        AvailabilitySlot availabilitySlot = availabilitySlotRepository.findOne(id);
        return availabilitySlot;
    }

    /**
     *  Delete the  availabilitySlot by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvailabilitySlot : {}", id);
        availabilitySlotRepository.delete(id);
    }
}
