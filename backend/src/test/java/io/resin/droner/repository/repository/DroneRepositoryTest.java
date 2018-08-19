package io.resin.droner.repository.repository;


import io.resin.droner.DronerApplication;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.repository.repository.DroneRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class DroneRepositoryTest {

    @Autowired
    private DroneRepository repository;

    @Test
    public void shouldInsertAndFillId() {
        // Arrange
        Drone drone = Drone.builder().build();
        //Act
        Drone saved = this.repository.saveOrUpdate(drone);
        //Assert
        assertThat(saved.getId(), notNullValue());
        assertThat(this.repository.all(), hasItem(saved));
    }

    @Test
    public void shouldInsertAndFindById() {
        // Arrange
        Drone drone = Drone.builder().build();
        //Act
        Drone saved = this.repository.saveOrUpdate(drone);
        Optional<Drone> fetched = this.repository.fetch(saved.getId());
        //Assert
        assertTrue(fetched.isPresent());
        assertThat(saved, equalTo(fetched.get()));
        assertThat(this.repository.all(), hasItem(saved));
    }

    @Test
    public void shouldDeleteByIdAndDontFind() {
        // Arrange
        Drone saved = this.repository.saveOrUpdate(Drone.builder().build());
        //Act
        this.repository.delete(saved.getId());
        //Assert
        assertThat(this.repository.all(), not(hasItem(saved)));
        assertFalse(this.repository.fetch(saved.getId()).isPresent());
    }

    @Test
    public void shouldInsertAndUpdate() {
        // Arrange
        Drone drone = Drone.builder().build();
        final Double EXPECTED_LATITUDE = 2D;
        final Double EXPECTED_LONGITUDE = 3D;
        //Act
        Drone saved = this.repository.saveOrUpdate(drone);
        saved.getCoordinates().add(Coordinate.builder()
                .time(Instant.now())
                .latitude(EXPECTED_LATITUDE)
                .longitude(EXPECTED_LONGITUDE)
                .build());
        Optional<Drone> fetched = this.repository.fetch(saved.getId());
        Coordinate coordinate = fetched.get().getCoordinates().get(0);
        //Assert
        assertTrue(fetched.isPresent());
        assertThat(fetched.get().getCoordinates().size(), equalTo(1));
        assertThat(coordinate.getLatitude(), equalTo(EXPECTED_LATITUDE));
        assertThat(coordinate.getLongitude(), equalTo(EXPECTED_LONGITUDE));
    }

    @Test
    public void shouldBeEmptyAfterPurgeAll() {
        // Arrange
        Drone drone = Drone.builder().build();
        this.repository.saveOrUpdate(drone);
        //Act
        this.repository.purgeAll();
        //Assert
        assertThat(this.repository.all(), empty());
    }

}
