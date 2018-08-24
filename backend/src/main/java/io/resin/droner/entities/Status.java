package io.resin.droner.entities;

/**
 * List of possible status of drones
 */
public enum Status {
    /**
     * Drone is moving regularly as expected
     */
    MOVING,
    /**
     * The drone is not sending any coordinate in the past 10 seconds
     */
    DEAD,
    /**
     * The drone is sending updates but not moving more then 10 meters in difference of the last coordinate sent
     */
    STUCK
}
