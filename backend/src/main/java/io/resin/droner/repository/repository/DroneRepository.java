package io.resin.droner.repository.repository;

import io.resin.droner.entities.Drone;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Interface that defines all persisting operations regarding drones
 */
public interface DroneRepository {

    /**
     * Fetches a drone by id if it exists or add a new one
     * @param drone
     * @return
     */
    Drone saveOrUpdate(Drone drone);

    /**
     * Fetch drone in the database based on the id
     * @param id Id of the drone to be fetched
     * @return Optional of Drone if it exists
     */
    Optional<Drone> fetch(UUID id);

    /**
     * Return all managed drones
     * @return Set of all drones in the database
     */
    Set<Drone> all();

    /**
     * Delete a drone of database
     * @param drone drone to be excluded
     * @return true if the operation succeed and false for not.
     */
    boolean delete(Drone drone);

    /**
     * Delete a drone of database by ID
     * @param id ID of the drone to be deleted
     * @return true if the operation succeed and false for not.
     */
    boolean delete(UUID id);

    /**
     * Purge all drones that are being managed
     */
    void purgeAll();
}
