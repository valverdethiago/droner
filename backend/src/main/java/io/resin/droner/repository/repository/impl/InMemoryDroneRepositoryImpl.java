package io.resin.droner.repository.repository.impl;

import io.resin.droner.repository.repository.DroneRepository;
import io.resin.droner.entities.Drone;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class InMemoryDroneRepositoryImpl implements DroneRepository {

    private Set<Drone> data;

    @PostConstruct
    public void init() {
        this.data = new HashSet<>();
    }

    @Override
    public void purgeAll() {
        this.init();
    }

    @Override
    public Drone saveOrUpdate(Drone drone) {
        if(drone.getId() == null) {
            drone.setId(UUID.randomUUID());
        }
        data.add(drone);
        return drone;
    }

    @Override
    public Optional<Drone> fetch(UUID id) {
        return data.stream().filter(drone -> id.equals(drone.getId())).findAny();
    }

    @Override
    public Set<Drone> all() {
        return data;
    }

    @Override
    public boolean delete(Drone drone) {
        return data.remove(drone);
    }

    @Override
    public boolean delete(UUID id) {
        Optional<Drone> drone = fetch(id);
        return drone.isPresent() ? this.delete(drone.get()) : false;
    }
}
