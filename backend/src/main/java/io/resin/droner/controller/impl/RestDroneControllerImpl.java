package io.resin.droner.controller.impl;

import io.resin.droner.controller.DroneController;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "/drone")
public class RestDroneControllerImpl implements DroneController {

    @Autowired
    private DroneService droneService;

    @Override
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Drone register() {
        return droneService.registerDrone();
    }

    @Override
    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCoordinates(@PathVariable("id") String id, @RequestBody Coordinate coordinate) {
        UUID uuid = UUID.fromString(id);
        this.droneService.updateCoordinates(uuid, coordinate);
    }

    @Override
    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Drone> all() {
        return this.droneService.listAll();
    }
}
