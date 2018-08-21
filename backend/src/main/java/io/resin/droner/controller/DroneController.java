package io.resin.droner.controller;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

public interface DroneController {
    Drone register();

    void updateCoordinates(@PathVariable("id") String id, @RequestBody Coordinate coordinate);

    Collection<Drone> all();
}
