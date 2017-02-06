package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.ParkingSpace;
import com.cs499.assignment2.repository.ParkingSpaceRepository;
import com.cs499.assignment2.service.ParkingSpaceService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParkingSpaceResource REST controller.
 *
 * @see ParkingSpaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class ParkingSpaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParkingSpaceMockMvc;

    private ParkingSpace parkingSpace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParkingSpaceResource parkingSpaceResource = new ParkingSpaceResource(parkingSpaceService);
        this.restParkingSpaceMockMvc = MockMvcBuilders.standaloneSetup(parkingSpaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingSpace createEntity(EntityManager em) {
        ParkingSpace parkingSpace = new ParkingSpace()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .expiration(DEFAULT_EXPIRATION);
        return parkingSpace;
    }

    @Before
    public void initTest() {
        parkingSpace = createEntity(em);
    }

    @Test
    @Transactional
    public void createParkingSpace() throws Exception {
        int databaseSizeBeforeCreate = parkingSpaceRepository.findAll().size();

        // Create the ParkingSpace

        restParkingSpaceMockMvc.perform(post("/api/parking-spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingSpace)))
            .andExpect(status().isCreated());

        // Validate the ParkingSpace in the database
        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeCreate + 1);
        ParkingSpace testParkingSpace = parkingSpaceList.get(parkingSpaceList.size() - 1);
        assertThat(testParkingSpace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParkingSpace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParkingSpace.getExpiration()).isEqualTo(DEFAULT_EXPIRATION);
    }

    @Test
    @Transactional
    public void createParkingSpaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parkingSpaceRepository.findAll().size();

        // Create the ParkingSpace with an existing ID
        ParkingSpace existingParkingSpace = new ParkingSpace();
        existingParkingSpace.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingSpaceMockMvc.perform(post("/api/parking-spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParkingSpace)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSpaceRepository.findAll().size();
        // set the field null
        parkingSpace.setName(null);

        // Create the ParkingSpace, which fails.

        restParkingSpaceMockMvc.perform(post("/api/parking-spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingSpace)))
            .andExpect(status().isBadRequest());

        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParkingSpaces() throws Exception {
        // Initialize the database
        parkingSpaceRepository.saveAndFlush(parkingSpace);

        // Get all the parkingSpaceList
        restParkingSpaceMockMvc.perform(get("/api/parking-spaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingSpace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].expiration").value(hasItem(DEFAULT_EXPIRATION.toString())));
    }

    @Test
    @Transactional
    public void getParkingSpace() throws Exception {
        // Initialize the database
        parkingSpaceRepository.saveAndFlush(parkingSpace);

        // Get the parkingSpace
        restParkingSpaceMockMvc.perform(get("/api/parking-spaces/{id}", parkingSpace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parkingSpace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.expiration").value(DEFAULT_EXPIRATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParkingSpace() throws Exception {
        // Get the parkingSpace
        restParkingSpaceMockMvc.perform(get("/api/parking-spaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParkingSpace() throws Exception {
        // Initialize the database
        parkingSpaceService.save(parkingSpace);

        int databaseSizeBeforeUpdate = parkingSpaceRepository.findAll().size();

        // Update the parkingSpace
        ParkingSpace updatedParkingSpace = parkingSpaceRepository.findOne(parkingSpace.getId());
        updatedParkingSpace
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .expiration(UPDATED_EXPIRATION);

        restParkingSpaceMockMvc.perform(put("/api/parking-spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParkingSpace)))
            .andExpect(status().isOk());

        // Validate the ParkingSpace in the database
        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeUpdate);
        ParkingSpace testParkingSpace = parkingSpaceList.get(parkingSpaceList.size() - 1);
        assertThat(testParkingSpace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParkingSpace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParkingSpace.getExpiration()).isEqualTo(UPDATED_EXPIRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingParkingSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSpaceRepository.findAll().size();

        // Create the ParkingSpace

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParkingSpaceMockMvc.perform(put("/api/parking-spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingSpace)))
            .andExpect(status().isCreated());

        // Validate the ParkingSpace in the database
        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParkingSpace() throws Exception {
        // Initialize the database
        parkingSpaceService.save(parkingSpace);

        int databaseSizeBeforeDelete = parkingSpaceRepository.findAll().size();

        // Get the parkingSpace
        restParkingSpaceMockMvc.perform(delete("/api/parking-spaces/{id}", parkingSpace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParkingSpace> parkingSpaceList = parkingSpaceRepository.findAll();
        assertThat(parkingSpaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingSpace.class);
    }
}
