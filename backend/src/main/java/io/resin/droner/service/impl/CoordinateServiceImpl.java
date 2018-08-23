package io.resin.droner.service.impl;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.service.CoordinateService;
import org.springframework.stereotype.Service;

import static java.lang.Math.*;

/**
 * Basic implementaion of all buisness rules related to coordinate
 */
@Service
public class CoordinateServiceImpl implements CoordinateService {
    private static final int EARTH_RADIUS = 6378137;
    static double PI_RAD = Math.PI / 180.0;


    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculateDistance(Coordinate start, Coordinate end) {
        return this.greatCircleInKilometers(start.getLatitude(), start.getLongitude(),
                end.getLatitude(), end.getLongitude()) * 1000;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coordinate moveVertically(Coordinate coordinate, Double distanceInMeters) {
        if (coordinate == null ||
                coordinate.getLongitude() == null ||
                coordinate.getLatitude() == null) {
            throw new IllegalArgumentException("latitude and longitude are mandatory for this calculation");
        }
        if(distanceInMeters == null || distanceInMeters < 0) {
            throw new IllegalArgumentException("Distance must be a valid positive number");
        }
        double latitude = coordinate.getLatitude() + (180 / Math.PI) * (distanceInMeters / EARTH_RADIUS);
        return Coordinate.builder().longitude(coordinate.getLongitude()).latitude(latitude).build();
    }

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
     * https://software.intel.com/en-us/blogs/2012/11/25/calculating-geographic-distances-in-location-aware-apps
     */
    private double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }
}
