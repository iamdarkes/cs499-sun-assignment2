package com.cs499.assignment2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.assignment2.domain.AvailabilitySlot;
import com.cs499.assignment2.service.AvailabilitySlotService;
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
 * REST controller for managing AvailabilitySlot.
 */
@RestController
@RequestMapping("/api")
public class AvailabilitySlotResource {

    private final Logger log = LoggerFactory.getLogger(AvailabilitySlotResource.class);

    private static final String ENTITY_NAME = "availabilitySlot";
        
    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotResource(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    /**
     * POST  /availability-slots : Create a new availabilitySlot.
     *
     * @param availabilitySlot the availabilitySlot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new availabilitySlot, or with status 400 (Bad Request) if the availabilitySlot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/availability-slots")
    @Timed
    public ResponseEntity<AvailabilitySlot> createAvailabilitySlot(@Valid @RequestBody AvailabilitySlot availabilitySlot) throws URISyntaxException {
        log.debug("REST request to save AvailabilitySlot : {}", availabilitySlot);
        if (availabilitySlot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new availabilitySlot cannot already have an ID")).body(null);
        }
        AvailabilitySlot result = availabilitySlotService.save(availabilitySlot);
        return ResponseEntity.created(new URI("/api/availability-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /availability-slots : Updates an existing availabilitySlot.
     *
     * @param availabilitySlot the availabilitySlot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated availabilitySlot,
     * or with status 400 (Bad Request) if the availabilitySlot is not valid,
     * or with status 500 (Internal Server Error) if the availabilitySlot couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/availability-slots")
    @Timed
    public ResponseEntity<AvailabilitySlot> updateAvailabilitySlot(@Valid @RequestBody AvailabilitySlot availabilitySlot) throws URISyntaxException {
        log.debug("REST request to update AvailabilitySlot : {}", availabilitySlot);
        if (availabilitySlot.getId() == null) {
            return createAvailabilitySlot(availabilitySlot);
        }
        AvailabilitySlot result = availabilitySlotService.save(availabilitySlot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, availabilitySlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /availability-slots : get all the availabilitySlots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of availabilitySlots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/availability-slots")
    @Timed
    public ResponseEntity<List<AvailabilitySlot>> getAllAvailabilitySlots(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AvailabilitySlots");
        Page<AvailabilitySlot> page = availabilitySlotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/availability-slots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /availability-slots/:id : get the "id" availabilitySlot.
     *
     * @param id the id of the availabilitySlot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the availabilitySlot, or with status 404 (Not Found)
     */
    @GetMapping("/availability-slots/{id}")
    @Timed
    public ResponseEntity<AvailabilitySlot> getAvailabilitySlot(@PathVariable Long id) {
        log.debug("REST request to get AvailabilitySlot : {}", id);
        AvailabilitySlot availabilitySlot = availabilitySlotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(availabilitySlot));
    }

    /**
     * DELETE  /availability-slots/:id : delete the "id" availabilitySlot.
     *
     * @param id the id of the availabilitySlot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/availability-slots/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvailabilitySlot(@PathVariable Long id) {
        log.debug("REST request to delete AvailabilitySlot : {}", id);
        availabilitySlotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
