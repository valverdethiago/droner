package io.resin.droner.entities;

import lombok.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
