package com.space.management.domain;

import com.space.management.repository.MissionEntity;
import java.util.Optional;

public interface MissionCommandRepository {
    void save(MissionEntity mission);
    Optional<MissionEntity> findByName(String name);
    void delete(String name);
}
