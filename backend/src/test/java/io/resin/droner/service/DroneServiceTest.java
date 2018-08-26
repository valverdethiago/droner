package io.resin.droner.service;


import io.resin.droner.DronerApplication;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.entities.Status;
import io.resin.droner.repository.repository.impl.InMemoryDroneRepositoryImpl;
import io.resin.droner.service.impl.DroneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static io.resin.droner.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class DroneServiceTest {

    @Autowired
    @InjectMocks
    private DroneServiceImpl service;
    @Mock
    private InMemoryDroneRepositoryImpl repository;
    @Autowired
    private CoordinateService coordinateService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldCallSaveWhenRegisteringDrone() {
        // Arrange is not needed here
        //Act
        service.registerDrone();
        //Assert
        verify(repository, times(1)).saveOrUpdate(Mockito.any(Drone.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionWhenUpdatingInvalidDrone() {
        // Arrange
        when(repository.fetch(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        //Act
        service.updateCoordinates(UUID.randomUUID(), Coordinate.builder().latitude(2D).latitude(1D).build());
        //Assert is not needed as we expect an exception to be thrown
    }

    @Test
    public void shouldUpdateCoordinates() {
        //Arrange
        Drone drone = Drone.builder().id(UUID.randomUUID()).build();
        when(repository.fetch(drone.getId())).thenReturn(Optional.of(drone));
        Coordinate coordinate = Coordinate.builder()
            .latitude(SAMPLE_LATITUDE)
            .longitude(SAMPLE_LONGITUDE)
            .build();
        //Act
        service.updateCoordinates(drone.getId(), coordinate);
        //Assert
        verify(repository, times(1)).saveOrUpdate(drone);
        assertThat(drone.getCoordinates(), not(empty()));
        assertThat(drone.getCoordinates().size(), equalTo(1));
    }

    @Test
    public void shouldReturnDroneInDeadStatus() {
        //Arrange
        Mockito.when(this.repository.saveOrUpdate(Mockito.any(Drone.class))).thenCallRealMethod();
        Mockito.when(this.repository.all()).thenCallRealMethod();
        Mockito.when(this.repository.fetch(Mockito.any(UUID.class))).thenCallRealMethod();
        doCallRealMethod().when(this.repository).init();
        this.repository.init();
        Drone drone = this.service.registerDrone();
        //Act
        Collection<Drone> drones = this.service.listAll();
        Drone fetchedDrone = this.service.fetch(drone.getId());
        //Assert
        assertThat(drones, hasItem(drone));
        assertThat(fetchedDrone.getStatus(), equalTo(Status.DEAD));
    }

    @Test
    public void shouldReturnDroneInMovingStatus() {
        //Arrange
        Mockito.when(this.repository.saveOrUpdate(Mockito.any(Drone.class))).thenCallRealMethod();
        Mockito.when(this.repository.all()).thenCallRealMethod();
        Mockito.when(this.repository.fetch(Mockito.any(UUID.class))).thenCallRealMethod();
        doCallRealMethod().when(this.repository).init();
        this.repository.init();
        Drone drone = this.service.registerDrone();
        //Act
        this.service.updateCoordinates(drone.getId(),
                Coordinate.builder()
                        .latitude(SAMPLE_LATITUDE)
                        .longitude(SAMPLE_LONGITUDE)
                        .build());
        Collection<Drone> drones = this.service.listAll();
        Drone fetchedDrone = this.service.fetch(drone.getId());
        //Assert
        assertThat(drones, hasItem(drone));
        assertThat(fetchedDrone.getStatus(), equalTo(Status.MOVING));
    }

    @Test
    public void shouldReturnDroneInStuckStatus() {
        //Arrange
        Instant initialTime = Instant.now().minusSeconds(100); //100 seconds before now
        Drone expected = Drone.builder()
                .id(UUID.randomUUID())
                .build();
        for(int i=0; i<= 10; i++) {
            initialTime = initialTime.plusSeconds(10);
            expected.getCoordinates().add(
                    Coordinate.builder()
                            .latitude(SAMPLE_LATITUDE)
                            .longitude(SAMPLE_LONGITUDE)
                            .time(initialTime)
                            .build());
        }
        Mockito.when(this.repository.fetch(Mockito.any(UUID.class))).thenReturn(Optional.of(expected));
        doCallRealMethod().when(this.repository).init();
        this.repository.init();
        //Act
        Drone fetchedDrone = this.service.fetch(expected.getId());
        //Assert
        assertThat(fetchedDrone.getStatus(), equalTo(Status.STUCK));
    }

    @Test
    public void shouldCalculateVelocity() {
        //Arrange
        Instant initialTime = Instant.now().minusSeconds(100); //100 seconds before now
        Drone expected = Drone.builder()
            .id(UUID.randomUUID())
            .build();
        Coordinate currentCoordinate =  Coordinate.builder()
                .latitude(SAMPLE_LATITUDE)
                .longitude(SAMPLE_LONGITUDE)
                .time(initialTime)
                .build();

        for(int i=0; i<= 10; i++) {
            initialTime = initialTime.plusSeconds(DEFAULT_TIME_DIFFERENCE);
            expected.getCoordinates().add(currentCoordinate);
            currentCoordinate = this.coordinateService
                .moveVertically(currentCoordinate, EXPECTED_DISTANCE);
            currentCoordinate.setTime(initialTime);
        }
        Mockito.when(this.repository.fetch(Mockito.any(UUID.class))).thenReturn(Optional.of(expected));
        doCallRealMethod().when(this.repository).init();
        this.repository.init();
        //Act
        Drone fetchedDrone = this.service.fetch(expected.getId());
        //Assert
        Double minExpectedVelocity = EXPECTED_VELOCITY - EXPECTED_VELOCITY * ERROR_MARGIN;
        Double maxExpectedVelocity = EXPECTED_VELOCITY + EXPECTED_VELOCITY * ERROR_MARGIN;
        assertThat(fetchedDrone.getStatus(), equalTo(Status.MOVING));
        assertThat(fetchedDrone.getVelocity(),
            is (both(greaterThanOrEqualTo(minExpectedVelocity)).and(lessThanOrEqualTo(maxExpectedVelocity))));
    }

}
