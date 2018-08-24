package io.resin.droner.repository.repository.impl;

import io.resin.droner.repository.repository.DroneRepository;
import io.resin.droner.entities.Drone;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * In memory implementation of DroneRepository
 */
@Repository
public class InMemoryDroneRepositoryImpl implements DroneRepository {

    /**
     * In memory object responsible to store the data
     */
    private Set<Drone> data;

    /**
     * Initialize the data on construct
     */
    @PostConstruct
    public void init() {
        this.data = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void purgeAll() {
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drone saveOrUpdate(Drone drone) {
        if(drone.getId() == null) {
            drone.setId(UUID.randomUUID());
        }
        data.add(drone);
        return drone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Drone> fetch(UUID id) {
        return data.stream().filter(drone -> id.equals(drone.getId())).findAny();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Drone> all() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Drone drone) {
        return data.remove(drone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(UUID id) {
        Optional<Drone> drone = fetch(id);
        return drone.isPresent() ? this.delete(drone.get()) : false;
    }
}
