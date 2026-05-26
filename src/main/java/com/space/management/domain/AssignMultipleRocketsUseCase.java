package com.space.management.domain;

import com.space.management.application.command.AssignMultipleRocketsCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.MissionEntity;
import com.space.management.repository.RocketEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;
import static com.space.management.common.exception.ErrorMessage.ROCKET_NOT_FOUND;

class AssignMultipleRocketsUseCase {
    private final RocketCommandRepository rocketRepository;
    private final MissionCommandRepository missionRepository;
    private final SpaceXDomainMapper mapper;

    AssignMultipleRocketsUseCase(RocketCommandRepository rocketRepository, MissionCommandRepository missionRepository, SpaceXDomainMapper mapper) {
        this.rocketRepository = rocketRepository;
        this.missionRepository = missionRepository;
        this.mapper = mapper;
    }

    void execute(AssignMultipleRocketsCommand command) {
        Mission mission = loadMission(command.missionName());

        assignRocketsToMission(command.rocketNames(), mission);

        saveMissionState(mission);
    }

    private Mission loadMission(String missionName) {
        return missionRepository.findByName(missionName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(MISSION_NOT_FOUND));
    }

    private void assignRocketsToMission(Iterable<String> rocketNames, Mission mission) {
        for (String rocketName : rocketNames) {
            Rocket rocket = loadRocket(rocketName);

            rocket.assignToMission(mission.getName());
            mission.assignRocket(rocket.getName());

            saveRocketState(rocket);
        }
    }

    private Rocket loadRocket(String rocketName) {
        return rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(ROCKET_NOT_FOUND));
    }

    private void saveRocketState(Rocket rocket) {
        RocketEntity updatedRocketEntity = mapper.toEntity(rocket);
        rocketRepository.save(updatedRocketEntity);
    }

    private void saveMissionState(Mission mission) {
        MissionEntity updatedMissionEntity = mapper.toEntity(mission);
        missionRepository.save(updatedMissionEntity);
    }
}
