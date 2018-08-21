package io.resin.droner.util;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class MockHelper {

    public static Drone mockDrone() {
        return Drone.builder().id(TestConstants.DEFAULT_DRONE_ID).build();
    }

    public static Coordinate mockCoordinate() {
        return Coordinate.builder()
                .time(Instant.now())
                .latitude(TestConstants.SAMPLE_LATITUDE)
                .longitude(TestConstants.SAMPLE_LONGITUDE)
                .build();
    }

    public static List<Drone> mockDroneList(int listSize) {
        List<Drone> result = new ArrayList<>();
        IntStream.range(0, listSize).forEach( index -> result.add(Drone.builder().id(UUID.randomUUID()).build()));
        return result;
    }
}
