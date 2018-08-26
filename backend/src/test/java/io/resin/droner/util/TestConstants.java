package io.resin.droner.util;

import java.util.UUID;

public class TestConstants {


    public static final UUID DEFAULT_DRONE_ID = UUID.randomUUID();
    public static final Double SAMPLE_LATITUDE = -30.0403059;
    public static final Double SAMPLE_LONGITUDE = -51.1542825;
    public static final Double DEFAULT_DISTANCE = 1d;
    public static final Double ERROR_MARGIN = 0.01;
    public static final Double EXPECTED_DISTANCE = 50D;
    public static final Integer DEFAULT_TIME_DIFFERENCE = 10;
    public static final Double EXPECTED_VELOCITY = EXPECTED_DISTANCE / DEFAULT_TIME_DIFFERENCE;
}
