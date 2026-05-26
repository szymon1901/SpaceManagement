package com.space.management.repository;

import com.space.management.domain.MissionCommandRepository;
import java.util.Optional;

class InMemoryMissionCommandRepository implements MissionCommandRepository {
    private final SpaceXDataStore dataStore;

    InMemoryMissionCommandRepository(SpaceXDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(MissionEntity entity) {

        dataStore.getMissionsTable().put(entity.getName(), entity);
    }

    @Override
    public Optional<MissionEntity> findByName(String name) {
        return Optional.ofNullable(dataStore.getMissionsTable().get(name));
    }

    @Override
    public void delete(String name) {
        dataStore.getMissionsTable().remove(name);
    }
}
