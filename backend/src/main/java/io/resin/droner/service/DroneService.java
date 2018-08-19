package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;

import java.util.UUID;

public interface DroneService {

    Drone registerDrone();

    void updateCoordinates(UUID id, Coordinate coordinate);

    boolean isAlive(UUID id);

    boolean isStuck(UUID id);


}
