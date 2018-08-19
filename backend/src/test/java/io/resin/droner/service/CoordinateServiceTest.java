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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class CoordinateServiceTest {

    private static final Coordinate COORDINATE_SAMPLE = Coordinate.builder().latitude(-30.0403059).longitude(-51.1542825).build();
    private static final Double DEFAULT_DISTANCE = 1d;
    private static final Double ERROR_MARGIN = 0.01;
    @Autowired
    private CoordinateService service;

    @Test
    public void shouldMoveVertically() {
        // Arrange is not needed here
        //Act
        Coordinate result = service.moveVertically(COORDINATE_SAMPLE, DEFAULT_DISTANCE);
        Double distance = service.calculateDistance(COORDINATE_SAMPLE, result);
        Double minExpectedDistance = distance - distance * ERROR_MARGIN;
        Double maxExpectedDistance = distance + distance * ERROR_MARGIN;
        //Assert
        assertThat(distance, is (both(greaterThanOrEqualTo(minExpectedDistance)).and(lessThanOrEqualTo(maxExpectedDistance))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithNullCoordinate() {
        //Arrange
        Coordinate invalidValue = Coordinate.builder().build();
        //Act
        service.moveVertically(invalidValue, DEFAULT_DISTANCE);
        //Assert is not needed here as we expect an exception to be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithNullDistance() {
        //Act
        service.moveVertically(COORDINATE_SAMPLE, null);
        //Assert is not needed here as we expect an exception to be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithInvalidDistance() {
        //Act
        service.moveVertically(COORDINATE_SAMPLE, -1d);
        //Assert is not needed here as we expect an exception to be thrown
    }
}
