package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;

import java.util.Collection;
import java.util.UUID;

public interface DroneService {

    Drone registerDrone();

    Collection<Drone> listAll();

    void updateCoordinates(UUID id, Coordinate coordinate) throws EntityNotFoundException;

    boolean isAlive(UUID id) throws EntityNotFoundException;

    boolean isStuck(UUID id) throws EntityNotFoundException;

    boolean isAlive(Drone drone);

    boolean isStuck(Drone drone);

    Drone fetch(UUID id) throws EntityNotFoundException;


}
