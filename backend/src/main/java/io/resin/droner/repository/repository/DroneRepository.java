package io.resin.droner.repository.repository;

import io.resin.droner.entities.Drone;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DroneRepository {

    Drone saveOrUpdate(Drone drone);
    Optional<Drone> fetch(UUID id);
    Set<Drone> all();
    boolean delete(Drone drone);
    boolean delete(UUID id);
    void purgeAll();
}
