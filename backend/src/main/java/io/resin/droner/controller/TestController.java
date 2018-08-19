package io.resin.droner.controller;


import io.resin.droner.repository.repository.DroneRepository;
import io.resin.droner.entities.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DroneRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public void test() {
        Drone drone = Drone.builder().id(UUID.randomUUID()).build();
        System.out.println("Testado: "+drone.getId());
    }
}
