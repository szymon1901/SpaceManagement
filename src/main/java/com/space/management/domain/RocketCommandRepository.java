package com.space.management.domain;

import com.space.management.repository.RocketEntity;
import java.util.Optional;

public interface RocketCommandRepository {
    void save(RocketEntity rocket);
    Optional<RocketEntity> findByName(String name);
    void delete(String name);
}
