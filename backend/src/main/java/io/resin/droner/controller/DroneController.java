package io.resin.droner.controller;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

/**
 * Interface conatining all operations needed to be exposed via drone controller
 */
public interface DroneController {

    /**
     * Register a drone in to the database
     * @return returns a drone object with a unique id
     */
    Drone register();

    /**
     * Update coordinates of a given drone identified by the id
     * @param id id of the drone being updated
     * @param coordinate Coordinates of the drone at that time
     */
    void updateCoordinates(@PathVariable("id") String id, @RequestBody Coordinate coordinate);

    /**
     * Fecthes all data related to drones, even their current status and coordinates sent.
     * @return Collection of all drones that has being monitored
     */
    Collection<Drone> all();
}
