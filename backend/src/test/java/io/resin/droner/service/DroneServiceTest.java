package io.resin.droner.service;


import io.resin.droner.DronerApplication;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.repository.repository.DroneRepository;
import io.resin.droner.service.impl.DroneServiceImpl;
import net.bytebuddy.pool.TypePool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class DroneServiceTest {

    private static final Double EXPECTED_LONGITUDE = 23D;
    private static final Double EXPECTED_LATITUDE = 25D;
    @Autowired
    @InjectMocks
    private DroneServiceImpl service;
    @Mock
    private DroneRepository repository;

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


    @Test(expected = IllegalArgumentException.class)
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
        Coordinate coordinate = Coordinate.builder().latitude(EXPECTED_LATITUDE).longitude(EXPECTED_LONGITUDE).build();
        //Act
        service.updateCoordinates(drone.getId(), coordinate);
        //Assert
        verify(repository, times(1)).saveOrUpdate(drone);
        assertThat(drone.getCoordinates(), not(empty()));
        assertThat(drone.getCoordinates().size(), equalTo(1));
    }

}
