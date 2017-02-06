package com.cs499.assignment2.service.impl;

import com.cs499.assignment2.service.ParkingSpaceService;
import com.cs499.assignment2.domain.ParkingSpace;
import com.cs499.assignment2.repository.ParkingSpaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ParkingSpace.
 */
@Service
@Transactional
public class ParkingSpaceServiceImpl implements ParkingSpaceService{

    private final Logger log = LoggerFactory.getLogger(ParkingSpaceServiceImpl.class);
    
    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceServiceImpl(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    /**
     * Save a parkingSpace.
     *
     * @param parkingSpace the entity to save
     * @return the persisted entity
     */
    @Override
    public ParkingSpace save(ParkingSpace parkingSpace) {
        log.debug("Request to save ParkingSpace : {}", parkingSpace);
        ParkingSpace result = parkingSpaceRepository.save(parkingSpace);
        return result;
    }

    /**
     *  Get all the parkingSpaces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParkingSpace> findAll(Pageable pageable) {
        log.debug("Request to get all ParkingSpaces");
        Page<ParkingSpace> result = parkingSpaceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one parkingSpace by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParkingSpace findOne(Long id) {
        log.debug("Request to get ParkingSpace : {}", id);
        ParkingSpace parkingSpace = parkingSpaceRepository.findOne(id);
        return parkingSpace;
    }

    /**
     *  Delete the  parkingSpace by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParkingSpace : {}", id);
        parkingSpaceRepository.delete(id);
    }
}
