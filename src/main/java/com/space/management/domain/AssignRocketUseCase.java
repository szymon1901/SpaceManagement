package com.space.management.domain;

import com.space.management.application.command.AssignRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.MissionEntity;
import com.space.management.repository.RocketEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;
import static com.space.management.common.exception.ErrorMessage.ROCKET_NOT_FOUND;

class AssignRocketUseCase {
    private final RocketCommandRepository rocketRepository;
    private final MissionCommandRepository missionRepository;
    private final SpaceXDomainMapper mapper;

    AssignRocketUseCase(RocketCommandRepository rocketRepository, MissionCommandRepository missionRepository, SpaceXDomainMapper mapper) {
        this.rocketRepository = rocketRepository;
        this.missionRepository = missionRepository;
        this.mapper = mapper;
    }

    void execute(AssignRocketCommand command) {
        Rocket rocket = loadRocket(command.rocketName());
        Mission mission = loadMission(command.missionName());

        rocket.assignToMission(mission.getName());
        mission.assignRocket(rocket.getName());

        saveRocketState(rocket);
        saveMissionState(mission);
    }

    private Rocket loadRocket(String rocketName) {
        return rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(ROCKET_NOT_FOUND));
    }

    private Mission loadMission(String missionName) {
        return missionRepository.findByName(missionName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(MISSION_NOT_FOUND));
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
