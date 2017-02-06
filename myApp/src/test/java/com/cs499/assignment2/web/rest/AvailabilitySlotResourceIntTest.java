package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.AvailabilitySlot;
import com.cs499.assignment2.repository.AvailabilitySlotRepository;
import com.cs499.assignment2.service.AvailabilitySlotService;

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

import com.cs499.assignment2.domain.enumeration.AvailabilityReason;
/**
 * Test class for the AvailabilitySlotResource REST controller.
 *
 * @see AvailabilitySlotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class AvailabilitySlotResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AvailabilityReason DEFAULT_AVAILABILITY_REASON = AvailabilityReason.LEAVE;
    private static final AvailabilityReason UPDATED_AVAILABILITY_REASON = AvailabilityReason.SICK;

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    @Autowired
    private AvailabilitySlotService availabilitySlotService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restAvailabilitySlotMockMvc;

    private AvailabilitySlot availabilitySlot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvailabilitySlotResource availabilitySlotResource = new AvailabilitySlotResource(availabilitySlotService);
        this.restAvailabilitySlotMockMvc = MockMvcBuilders.standaloneSetup(availabilitySlotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvailabilitySlot createEntity(EntityManager em) {
        AvailabilitySlot availabilitySlot = new AvailabilitySlot()
                .description(DEFAULT_DESCRIPTION)
                .availabilityReason(DEFAULT_AVAILABILITY_REASON)
                .fromDate(DEFAULT_FROM_DATE)
                .toDate(DEFAULT_TO_DATE);
        return availabilitySlot;
    }

    @Before
    public void initTest() {
        availabilitySlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvailabilitySlot() throws Exception {
        int databaseSizeBeforeCreate = availabilitySlotRepository.findAll().size();

        // Create the AvailabilitySlot

        restAvailabilitySlotMockMvc.perform(post("/api/availability-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilitySlot)))
            .andExpect(status().isCreated());

        // Validate the AvailabilitySlot in the database
        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeCreate + 1);
        AvailabilitySlot testAvailabilitySlot = availabilitySlotList.get(availabilitySlotList.size() - 1);
        assertThat(testAvailabilitySlot.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAvailabilitySlot.getAvailabilityReason()).isEqualTo(DEFAULT_AVAILABILITY_REASON);
        assertThat(testAvailabilitySlot.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testAvailabilitySlot.getToDate()).isEqualTo(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void createAvailabilitySlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = availabilitySlotRepository.findAll().size();

        // Create the AvailabilitySlot with an existing ID
        AvailabilitySlot existingAvailabilitySlot = new AvailabilitySlot();
        existingAvailabilitySlot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvailabilitySlotMockMvc.perform(post("/api/availability-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAvailabilitySlot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAvailabilityReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = availabilitySlotRepository.findAll().size();
        // set the field null
        availabilitySlot.setAvailabilityReason(null);

        // Create the AvailabilitySlot, which fails.

        restAvailabilitySlotMockMvc.perform(post("/api/availability-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilitySlot)))
            .andExpect(status().isBadRequest());

        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvailabilitySlots() throws Exception {
        // Initialize the database
        availabilitySlotRepository.saveAndFlush(availabilitySlot);

        // Get all the availabilitySlotList
        restAvailabilitySlotMockMvc.perform(get("/api/availability-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availabilitySlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].availabilityReason").value(hasItem(DEFAULT_AVAILABILITY_REASON.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(sameInstant(DEFAULT_TO_DATE))));
    }

    @Test
    @Transactional
    public void getAvailabilitySlot() throws Exception {
        // Initialize the database
        availabilitySlotRepository.saveAndFlush(availabilitySlot);

        // Get the availabilitySlot
        restAvailabilitySlotMockMvc.perform(get("/api/availability-slots/{id}", availabilitySlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(availabilitySlot.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.availabilityReason").value(DEFAULT_AVAILABILITY_REASON.toString()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.toDate").value(sameInstant(DEFAULT_TO_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAvailabilitySlot() throws Exception {
        // Get the availabilitySlot
        restAvailabilitySlotMockMvc.perform(get("/api/availability-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvailabilitySlot() throws Exception {
        // Initialize the database
        availabilitySlotService.save(availabilitySlot);

        int databaseSizeBeforeUpdate = availabilitySlotRepository.findAll().size();

        // Update the availabilitySlot
        AvailabilitySlot updatedAvailabilitySlot = availabilitySlotRepository.findOne(availabilitySlot.getId());
        updatedAvailabilitySlot
                .description(UPDATED_DESCRIPTION)
                .availabilityReason(UPDATED_AVAILABILITY_REASON)
                .fromDate(UPDATED_FROM_DATE)
                .toDate(UPDATED_TO_DATE);

        restAvailabilitySlotMockMvc.perform(put("/api/availability-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvailabilitySlot)))
            .andExpect(status().isOk());

        // Validate the AvailabilitySlot in the database
        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeUpdate);
        AvailabilitySlot testAvailabilitySlot = availabilitySlotList.get(availabilitySlotList.size() - 1);
        assertThat(testAvailabilitySlot.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAvailabilitySlot.getAvailabilityReason()).isEqualTo(UPDATED_AVAILABILITY_REASON);
        assertThat(testAvailabilitySlot.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testAvailabilitySlot.getToDate()).isEqualTo(UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvailabilitySlot() throws Exception {
        int databaseSizeBeforeUpdate = availabilitySlotRepository.findAll().size();

        // Create the AvailabilitySlot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvailabilitySlotMockMvc.perform(put("/api/availability-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilitySlot)))
            .andExpect(status().isCreated());

        // Validate the AvailabilitySlot in the database
        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvailabilitySlot() throws Exception {
        // Initialize the database
        availabilitySlotService.save(availabilitySlot);

        int databaseSizeBeforeDelete = availabilitySlotRepository.findAll().size();

        // Get the availabilitySlot
        restAvailabilitySlotMockMvc.perform(delete("/api/availability-slots/{id}", availabilitySlot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AvailabilitySlot> availabilitySlotList = availabilitySlotRepository.findAll();
        assertThat(availabilitySlotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvailabilitySlot.class);
    }
}
