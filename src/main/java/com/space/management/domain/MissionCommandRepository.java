package com.space.management.domain;

import java.util.Optional;

public interface MissionCommandRepository {
    void save(Mission mission);
    Optional<Mission> findByName(String name);
}
