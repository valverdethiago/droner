package io.resin.droner.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class Coordinate implements Serializable, Comparable<Coordinate> {
    private Instant time;
    private final Double latitude;
    private final Double longitude;

    @Override
    public int compareTo(Coordinate another) {
        if(this == another || (this.getTime() == null && another.getTime() == null)) {
            return 0;
        }
        return this.getTime().compareTo(another.getTime());
    }
}
