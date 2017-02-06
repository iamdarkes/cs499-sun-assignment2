package com.cs499.assignment2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.assignment2.domain.ParkingSpace;
import com.cs499.assignment2.service.ParkingSpaceService;
import com.cs499.assignment2.web.rest.util.HeaderUtil;
import com.cs499.assignment2.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ParkingSpace.
 */
@RestController
@RequestMapping("/api")
public class ParkingSpaceResource {

    private final Logger log = LoggerFactory.getLogger(ParkingSpaceResource.class);

    private static final String ENTITY_NAME = "parkingSpace";
        
    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceResource(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    /**
     * POST  /parking-spaces : Create a new parkingSpace.
     *
     * @param parkingSpace the parkingSpace to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parkingSpace, or with status 400 (Bad Request) if the parkingSpace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parking-spaces")
    @Timed
    public ResponseEntity<ParkingSpace> createParkingSpace(@Valid @RequestBody ParkingSpace parkingSpace) throws URISyntaxException {
        log.debug("REST request to save ParkingSpace : {}", parkingSpace);
        if (parkingSpace.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parkingSpace cannot already have an ID")).body(null);
        }
        ParkingSpace result = parkingSpaceService.save(parkingSpace);
        return ResponseEntity.created(new URI("/api/parking-spaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parking-spaces : Updates an existing parkingSpace.
     *
     * @param parkingSpace the parkingSpace to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parkingSpace,
     * or with status 400 (Bad Request) if the parkingSpace is not valid,
     * or with status 500 (Internal Server Error) if the parkingSpace couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parking-spaces")
    @Timed
    public ResponseEntity<ParkingSpace> updateParkingSpace(@Valid @RequestBody ParkingSpace parkingSpace) throws URISyntaxException {
        log.debug("REST request to update ParkingSpace : {}", parkingSpace);
        if (parkingSpace.getId() == null) {
            return createParkingSpace(parkingSpace);
        }
        ParkingSpace result = parkingSpaceService.save(parkingSpace);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parkingSpace.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parking-spaces : get all the parkingSpaces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parkingSpaces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/parking-spaces")
    @Timed
    public ResponseEntity<List<ParkingSpace>> getAllParkingSpaces(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ParkingSpaces");
        Page<ParkingSpace> page = parkingSpaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parking-spaces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parking-spaces/:id : get the "id" parkingSpace.
     *
     * @param id the id of the parkingSpace to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parkingSpace, or with status 404 (Not Found)
     */
    @GetMapping("/parking-spaces/{id}")
    @Timed
    public ResponseEntity<ParkingSpace> getParkingSpace(@PathVariable Long id) {
        log.debug("REST request to get ParkingSpace : {}", id);
        ParkingSpace parkingSpace = parkingSpaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parkingSpace));
    }

    /**
     * DELETE  /parking-spaces/:id : delete the "id" parkingSpace.
     *
     * @param id the id of the parkingSpace to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parking-spaces/{id}")
    @Timed
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        log.debug("REST request to delete ParkingSpace : {}", id);
        parkingSpaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
