package com.space.management.domain;

import com.space.management.application.command.ChangeMissionStatusCommand;
import com.space.management.common.MissionStatus;
import com.space.management.common.exception.BusinessValidationException;

import static com.space.management.common.MissionStatus.ENDED;
import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;

class ChangeMissionStatusUseCase {
    private final MissionCommandRepository missionRepository;
    private final RocketCommandRepository rocketRepository;
    final SpaceXDomainMapper mapper;

    ChangeMissionStatusUseCase(MissionCommandRepository missionRepository, RocketCommandRepository rocketRepository, SpaceXDomainMapper mapper) {
        this.missionRepository = missionRepository;
        this.rocketRepository = rocketRepository;
        this.mapper = mapper;
    }

    void execute(ChangeMissionStatusCommand command) {
        Mission mission = loadMission(command.missionName());

        processStatusChange(mission, command.newStatus());

        saveMissionState(mission);
    }

    private void processStatusChange(Mission mission, MissionStatus newStatus) {
        if (newStatus == ENDED) {
            releaseAllAssignedRockets(mission);
            mission.endMission();
        } else {
            mission.changeStatus(newStatus);
        }
    }

    private void releaseAllAssignedRockets(Mission mission) {
        mission.getAssignedRocketNames().forEach(this::releaseRocket);
    }

    private void releaseRocket(String rocketName) {
        rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .ifPresent(rocket -> {
                rocket.releaseFromMission();
                saveRocketState(rocket);
            });
    }

    private Mission loadMission(String missionName) {
        return missionRepository.findByName(missionName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(MISSION_NOT_FOUND));
    }

    private void saveRocketState(Rocket rocket) {
        rocketRepository.save(mapper.toEntity(rocket));
    }

    private void saveMissionState(Mission mission) {
        missionRepository.save(mapper.toEntity(mission));
    }
}
