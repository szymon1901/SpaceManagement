package com.space.management.repository;

import com.space.management.domain.Rocket;
import com.space.management.domain.RocketCommandRepository;
import java.util.Optional;

public class InMemoryRocketCommandRepository implements RocketCommandRepository {

    private final SpaceXDataStore dataStore;

    public InMemoryRocketCommandRepository(SpaceXDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(Rocket rocket) {
        RocketEntity entity = new RocketEntity(
            rocket.getName(),
            rocket.getStatus(),
            rocket.getAssignedMissionName()
        );

        dataStore.getRocketsTable().put(rocket.getName(), entity);
    }

    @Override
    public Optional<Rocket> findByName(String name) {
        return Optional.ofNullable(dataStore.getRocketsTable().get(name))
            .map(entity -> new Rocket(entity.getName(), entity.getStatus(), entity.getAssignedMissionName()));
    }
}
