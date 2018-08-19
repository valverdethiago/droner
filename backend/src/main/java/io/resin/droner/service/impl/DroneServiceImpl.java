package io.resin.droner.service.impl;

import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.repository.repository.DroneRepository;
import io.resin.droner.service.CoordinateService;
import io.resin.droner.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DroneServiceImpl implements DroneService {

    private static final int MINIMUM_INTERVAL = 10;

    @Autowired
    private DroneRepository repository;
    @Autowired
    private CoordinateService coordinateService;

    @Override
    public Drone registerDrone() {
        return repository.saveOrUpdate(Drone.builder().build());
    }

    @Override
    public void updateCoordinates(UUID id, Coordinate coordinate) {
        Optional<Drone> drone = this.repository.fetch(id);
        if (!drone.isPresent()) {
            throw new IllegalArgumentException(String.format("Drone with id %s not found", id));
        }
        coordinate.setTime(Instant.now());
        drone.get().getCoordinates().add(coordinate);
        this.repository.saveOrUpdate(drone.get());
    }

    @Override
    public boolean isAlive(UUID id) {
        Optional<Drone> drone = this.repository.fetch(id);
        if (!drone.isPresent()) {
            throw new IllegalArgumentException(String.format("Drone with id %s not found", id));
        }
        Instant now = Instant.now();
        //filter coordinates sent in the last 10 seconds
        List<Coordinate> recentSentCoordinates = drone.get().getCoordinates()
                .stream()
                .filter(coordinate ->
                        coordinate.getTime().plusSeconds(MINIMUM_INTERVAL).isBefore(now))
                .collect(Collectors.toList());
        return !recentSentCoordinates.isEmpty();
    }

    @Override
    public boolean isStuck(UUID id) {
        Optional<Drone> drone = this.repository.fetch(id);
        if (!drone.isPresent()) {
            throw new IllegalArgumentException(String.format("Drone with id %s not found", id));
        }
        Instant now = Instant.now();
        //filter coordinates sent in the last 10 seconds
        List<Coordinate> recentSentCoordinates = drone.get().getCoordinates()
                .stream()
                .filter(coordinate ->
                        coordinate.getTime().plusSeconds(MINIMUM_INTERVAL).isBefore(now))
                .collect(Collectors.toList());
        if (recentSentCoordinates.size() <= 1) {
            return true;
        }
        Collections.sort(recentSentCoordinates);
        Coordinate first = recentSentCoordinates.get(0);
        Coordinate last = recentSentCoordinates.get(recentSentCoordinates.size() -1);
        Double distance = this.coordinateService.calculateDistance(first, last);
        return distance <= 1;
    }
}
