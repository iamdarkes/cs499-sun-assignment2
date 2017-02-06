package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.BookingSlot;
import com.cs499.assignment2.repository.BookingSlotRepository;
import com.cs499.assignment2.service.BookingSlotService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.cs499.assignment2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookingSlotResource REST controller.
 *
 * @see BookingSlotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class BookingSlotResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BookingSlotRepository bookingSlotRepository;

    @Autowired
    private BookingSlotService bookingSlotService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restBookingSlotMockMvc;

    private BookingSlot bookingSlot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingSlotResource bookingSlotResource = new BookingSlotResource(bookingSlotService);
        this.restBookingSlotMockMvc = MockMvcBuilders.standaloneSetup(bookingSlotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingSlot createEntity(EntityManager em) {
        BookingSlot bookingSlot = new BookingSlot()
                .description(DEFAULT_DESCRIPTION)
                .fromDate(DEFAULT_FROM_DATE)
                .toDate(DEFAULT_TO_DATE);
        return bookingSlot;
    }

    @Before
    public void initTest() {
        bookingSlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookingSlot() throws Exception {
        int databaseSizeBeforeCreate = bookingSlotRepository.findAll().size();

        // Create the BookingSlot

        restBookingSlotMockMvc.perform(post("/api/booking-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlot)))
            .andExpect(status().isCreated());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeCreate + 1);
        BookingSlot testBookingSlot = bookingSlotList.get(bookingSlotList.size() - 1);
        assertThat(testBookingSlot.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBookingSlot.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testBookingSlot.getToDate()).isEqualTo(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void createBookingSlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingSlotRepository.findAll().size();

        // Create the BookingSlot with an existing ID
        BookingSlot existingBookingSlot = new BookingSlot();
        existingBookingSlot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingSlotMockMvc.perform(post("/api/booking-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBookingSlot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookingSlots() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        // Get all the bookingSlotList
        restBookingSlotMockMvc.perform(get("/api/booking-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(sameInstant(DEFAULT_TO_DATE))));
    }

    @Test
    @Transactional
    public void getBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        // Get the bookingSlot
        restBookingSlotMockMvc.perform(get("/api/booking-slots/{id}", bookingSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookingSlot.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.toDate").value(sameInstant(DEFAULT_TO_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingBookingSlot() throws Exception {
        // Get the bookingSlot
        restBookingSlotMockMvc.perform(get("/api/booking-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotService.save(bookingSlot);

        int databaseSizeBeforeUpdate = bookingSlotRepository.findAll().size();

        // Update the bookingSlot
        BookingSlot updatedBookingSlot = bookingSlotRepository.findOne(bookingSlot.getId());
        updatedBookingSlot
                .description(UPDATED_DESCRIPTION)
                .fromDate(UPDATED_FROM_DATE)
                .toDate(UPDATED_TO_DATE);

        restBookingSlotMockMvc.perform(put("/api/booking-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookingSlot)))
            .andExpect(status().isOk());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeUpdate);
        BookingSlot testBookingSlot = bookingSlotList.get(bookingSlotList.size() - 1);
        assertThat(testBookingSlot.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBookingSlot.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testBookingSlot.getToDate()).isEqualTo(UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBookingSlot() throws Exception {
        int databaseSizeBeforeUpdate = bookingSlotRepository.findAll().size();

        // Create the BookingSlot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookingSlotMockMvc.perform(put("/api/booking-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlot)))
            .andExpect(status().isCreated());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotService.save(bookingSlot);

        int databaseSizeBeforeDelete = bookingSlotRepository.findAll().size();

        // Get the bookingSlot
        restBookingSlotMockMvc.perform(delete("/api/booking-slots/{id}", bookingSlot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingSlot.class);
    }
}
