package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DroneService {

    Drone registerDrone();

    Collection<Drone> listAll();

    void updateCoordinates(UUID id, Coordinate coordinate);

    boolean isAlive(UUID id);

    boolean isStuck(UUID id);

    boolean isAlive(Drone drone);

    boolean isStuck(Drone drone);

    Drone fetch(UUID id);


}
