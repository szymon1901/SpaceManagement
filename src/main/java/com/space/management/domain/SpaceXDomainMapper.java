package com.space.management.domain;

import com.space.management.repository.MissionEntity;
import com.space.management.repository.RocketEntity;

class SpaceXDomainMapper {
    Rocket toDomain(RocketEntity entity) {
        return new Rocket(entity.getName(), entity.getStatus(), entity.getAssignedMissionName());
    }

    RocketEntity toEntity(Rocket rocket) {
        return new RocketEntity(rocket.getName(), rocket.getStatus(), rocket.getAssignedMissionName());
    }

    Mission toDomain(MissionEntity entity) {
        return new Mission(entity.getName(), entity.getStatus(), entity.getRocketNames());
    }

    MissionEntity toEntity(Mission mission) {
        return new MissionEntity(mission.getName(), mission.getStatus(), mission.getAssignedRocketNames());
    }
}
