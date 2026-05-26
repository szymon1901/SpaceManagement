package com.space.management.domain;

import com.space.management.repository.RocketEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TestInMemoryRocketCommandRepository implements RocketCommandRepository {
    private final Map<String, RocketEntity> storage = new HashMap<>();

    @Override
    public void save(RocketEntity rocketEntity) {
        storage.put(rocketEntity.getName(), rocketEntity);
    }

    @Override
    public Optional<RocketEntity> findByName(String name) {
        return Optional.ofNullable(storage.get(name));
    }

    @Override
    public void delete(String name) {
        storage.remove(name);
    }
}
