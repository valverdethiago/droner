package io.resin.droner.entities;

import lombok.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Map class that represents the drones, with their unique identifiable id, status and coordinates
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Drone implements Serializable {

    private UUID id;
    private Status status;
    private Coordinate lastCoordinate;

    @Singular
    private final List<Coordinate> coordinates = new LinkedList<>();

}
