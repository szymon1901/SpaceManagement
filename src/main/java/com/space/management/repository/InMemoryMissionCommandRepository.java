package com.space.management.repository;

import com.space.management.domain.Mission;
import com.space.management.domain.MissionCommandRepository;
import java.util.Optional;

public class InMemoryMissionCommandRepository implements MissionCommandRepository {
    private final SpaceXDataStore dataStore;

    public InMemoryMissionCommandRepository(SpaceXDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(Mission mission) {
        MissionEntity entity = new MissionEntity(
            mission.getName(),
            mission.getStatus(),
            mission.getAssignedRocketNames()
        );

        dataStore.getMissionsTable().put(mission.getName(), entity);
    }

    @Override
    public Optional<Mission> findByName(String name) {
        return Optional.ofNullable(dataStore.getMissionsTable().get(name))
            .map(entity -> new Mission(entity.getName(), entity.getStatus(), entity.getRocketNames()));
    }
}
