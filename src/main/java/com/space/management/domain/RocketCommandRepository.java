package com.space.management.domain;

import java.util.Optional;

public interface RocketCommandRepository {
    void save(Rocket rocket);
    Optional<Rocket> findByName(String name);
}
