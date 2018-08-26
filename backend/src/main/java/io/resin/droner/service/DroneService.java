package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;

import java.util.Collection;
import java.util.UUID;

/**
 * Service class that wraps all business operations related to Drone entities
 *
 */
public interface DroneService {

    /**
     * Register a new drone to be managed
     * @return Drone with id
     */
    Drone registerDrone();

    /**
     * List all managed drones in the database
     * @return
     */
    Collection<Drone> listAll();

    /**
     * Update a coordinate of a given drone that corresponds to the id passed
     * @param id Drone id
     * @param coordinate Coordinate to be updated
     * @throws EntityNotFoundException will be thrown if it's not possible to find a drone with the given id
     */
    void updateCoordinates(UUID id, Coordinate coordinate) throws EntityNotFoundException;

    /**
     * Checks if a drone is still alive
     * @param id  Drone id
     * @return true if the drone is active and false if it is stuck or dead
     * @throws EntityNotFoundException will be thrown if it's not possible to find a drone with the given id
     */
    boolean isAlive(UUID id) throws EntityNotFoundException;

    /**
     * Checks if a drone is stuck
     * @param id Drone id
     * @return true if the drone is stuck and false if it is stuck or dead
     * @throws EntityNotFoundException will be thrown if it's not possible to find a drone with the given id
     */
    boolean isStuck(UUID id) throws EntityNotFoundException;

    /**
     * Checks if a drone is still alive
     * @param drone drone to be checked
     * @return true if the drone is active and false if it is stuck or dead
     */
    boolean isAlive(Drone drone);

    /**
     * Checks if a drone is stuck
     * @param drone drone to be checked
     * @return true if the drone is active and false if it is stuck or dead
     */
    boolean isStuck(Drone drone);

    /**
     * Fetch a drone from the database and populate your complete state
     * @param id Drone id
     * @return Entity found
     * @throws EntityNotFoundException  will be thrown if it's not possible to find a drone with the given id
     */
    Drone fetch(UUID id) throws EntityNotFoundException;

}
