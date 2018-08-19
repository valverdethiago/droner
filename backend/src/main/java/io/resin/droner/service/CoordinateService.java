package io.resin.droner.service;

import io.resin.droner.entities.Coordinate;

public interface CoordinateService {

    Double calculateDistance(Coordinate start, Coordinate end);


    Coordinate moveVertically(Coordinate coordinate, Double distanceInMeters);
}
