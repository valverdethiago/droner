package io.resin.droner.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class Coordinate implements Serializable {
    private final Instant time;
    private final Double latitude;
    private final Double longitude;
}
