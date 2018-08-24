package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;

/**
 * Basic interface for buisness operations regargind Coordinate object
 */
public interface CoordinateService {

    /**
     * Calculates the distance between two coordinates
     * @param start initial coordinate
     * @param end final coordinate
     * @return distance in meters
     */
    Double calculateDistance(Coordinate start, Coordinate end);

    /**
     * Moves a coordinate vertically (aka, in axis x)
     * @param coordinate initial coordinate
     * @param distanceInMeters Distance to be added
     * @return Updated coordinate
     */
    Coordinate moveVertically(Coordinate coordinate, Double distanceInMeters);
}
