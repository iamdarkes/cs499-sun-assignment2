package com.cs499.assignment2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.assignment2.domain.BookingSlot;
import com.cs499.assignment2.service.BookingSlotService;
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
 * REST controller for managing BookingSlot.
 */
@RestController
@RequestMapping("/api")
public class BookingSlotResource {

    private final Logger log = LoggerFactory.getLogger(BookingSlotResource.class);

    private static final String ENTITY_NAME = "bookingSlot";
        
    private final BookingSlotService bookingSlotService;

    public BookingSlotResource(BookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

    /**
     * POST  /booking-slots : Create a new bookingSlot.
     *
     * @param bookingSlot the bookingSlot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookingSlot, or with status 400 (Bad Request) if the bookingSlot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/booking-slots")
    @Timed
    public ResponseEntity<BookingSlot> createBookingSlot(@Valid @RequestBody BookingSlot bookingSlot) throws URISyntaxException {
        log.debug("REST request to save BookingSlot : {}", bookingSlot);
        if (bookingSlot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bookingSlot cannot already have an ID")).body(null);
        }
        BookingSlot result = bookingSlotService.save(bookingSlot);
        return ResponseEntity.created(new URI("/api/booking-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /booking-slots : Updates an existing bookingSlot.
     *
     * @param bookingSlot the bookingSlot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookingSlot,
     * or with status 400 (Bad Request) if the bookingSlot is not valid,
     * or with status 500 (Internal Server Error) if the bookingSlot couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/booking-slots")
    @Timed
    public ResponseEntity<BookingSlot> updateBookingSlot(@Valid @RequestBody BookingSlot bookingSlot) throws URISyntaxException {
        log.debug("REST request to update BookingSlot : {}", bookingSlot);
        if (bookingSlot.getId() == null) {
            return createBookingSlot(bookingSlot);
        }
        BookingSlot result = bookingSlotService.save(bookingSlot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookingSlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /booking-slots : get all the bookingSlots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookingSlots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/booking-slots")
    @Timed
    public ResponseEntity<List<BookingSlot>> getAllBookingSlots(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BookingSlots");
        Page<BookingSlot> page = bookingSlotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/booking-slots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /booking-slots/:id : get the "id" bookingSlot.
     *
     * @param id the id of the bookingSlot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookingSlot, or with status 404 (Not Found)
     */
    @GetMapping("/booking-slots/{id}")
    @Timed
    public ResponseEntity<BookingSlot> getBookingSlot(@PathVariable Long id) {
        log.debug("REST request to get BookingSlot : {}", id);
        BookingSlot bookingSlot = bookingSlotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookingSlot));
    }

    /**
     * DELETE  /booking-slots/:id : delete the "id" bookingSlot.
     *
     * @param id the id of the bookingSlot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/booking-slots/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookingSlot(@PathVariable Long id) {
        log.debug("REST request to delete BookingSlot : {}", id);
        bookingSlotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
