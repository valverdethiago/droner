package io.resin.droner.service;


import io.resin.droner.DronerApplication;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.util.TestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.resin.droner.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class CoordinateServiceTest {
    @Autowired
    private CoordinateService service;
    private static final Coordinate SAMPLE_COORDINATE = Coordinate.builder()
            .latitude(TestConstants.SAMPLE_LATITUDE)
            .longitude(TestConstants.SAMPLE_LONGITUDE)
            .build();

    @Test
    public void shouldMoveVertically() {
        // Arrange is not needed here
        //Act
        Coordinate result = service.moveVertically(SAMPLE_COORDINATE, DEFAULT_DISTANCE);
        Double distance = service.calculateDistance(SAMPLE_COORDINATE, result);
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
        service.moveVertically(SAMPLE_COORDINATE, null);
        //Assert is not needed here as we expect an exception to be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithInvalidDistance() {
        //Act
        service.moveVertically(SAMPLE_COORDINATE, -1d);
        //Assert is not needed here as we expect an exception to be thrown
    }
}
