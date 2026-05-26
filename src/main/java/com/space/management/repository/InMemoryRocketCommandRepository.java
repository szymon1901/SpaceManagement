package com.space.management.repository;

import com.space.management.domain.RocketCommandRepository;
import java.util.Optional;

class InMemoryRocketCommandRepository implements RocketCommandRepository {

    private final SpaceXDataStore dataStore;

    InMemoryRocketCommandRepository(SpaceXDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(RocketEntity entity) {
        dataStore.getRocketsTable()
            .put(entity.getName(), entity);
    }

    @Override
    public Optional<RocketEntity> findByName(String name) {
        return Optional.ofNullable(dataStore.getRocketsTable().get(name));
    }

    @Override
    public void delete(String name) {
        dataStore.getRocketsTable().remove(name);
    }
}
