package com.space.management.domain;

import com.space.management.repository.MissionEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TestInMemoryMissionCommandRepository implements MissionCommandRepository {
    private final Map<String, MissionEntity> storage = new HashMap<>();

    @Override
    public void save(MissionEntity missionEntity) {
        storage.put(missionEntity.getName(), missionEntity);
    }

    @Override
    public Optional<MissionEntity> findByName(String name) {
        return Optional.ofNullable(storage.get(name));
    }

    @Override
    public void delete(String name) {
        storage.remove(name);
    }
}
