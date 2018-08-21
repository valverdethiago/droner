package io.resin.droner.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coordinate implements Serializable, Comparable<Coordinate> {
    private Instant time;
    private Double latitude;
    private Double longitude;

    @Override
    public int compareTo(Coordinate another) {
        if(this == another || (this.getTime() == null && another.getTime() == null)) {
            return 0;
        }
        return this.getTime().compareTo(another.getTime());
    }
}
