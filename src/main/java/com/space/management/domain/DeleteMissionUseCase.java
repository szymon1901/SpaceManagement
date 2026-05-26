package com.space.management.domain;

import com.space.management.application.command.DeleteMissionCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.RocketEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;

class DeleteMissionUseCase {

    private final MissionCommandRepository missionRepository;
    private final RocketCommandRepository rocketRepository;
    private final SpaceXDomainMapper mapper;

    DeleteMissionUseCase(MissionCommandRepository missionRepository, RocketCommandRepository rocketRepository, SpaceXDomainMapper mapper) {
        this.missionRepository = missionRepository;
        this.rocketRepository = rocketRepository;
        this.mapper = mapper;
    }

    void execute(DeleteMissionCommand command) {
        Mission mission = loadMission(command.missionName());

        releaseAssignedRockets(mission);

        deleteMissionFromSystem(command.missionName());
    }

    private Mission loadMission(String missionName) {
        return missionRepository.findByName(missionName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(MISSION_NOT_FOUND));
    }

    private void releaseAssignedRockets(Mission mission) {
        for (String rocketName : mission.getAssignedRocketNames()) {
            releaseRocket(rocketName);
        }
    }

    private void releaseRocket(String rocketName) {
        rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .ifPresent(rocket -> {
                rocket.assignToMission(null);

                saveRocketState(rocket);
            });
    }

    private void saveRocketState(Rocket rocket) {
        RocketEntity updatedRocketEntity = mapper.toEntity(rocket);
        rocketRepository.save(updatedRocketEntity);
    }

    private void deleteMissionFromSystem(String missionName) {
        missionRepository.delete(missionName);
    }
}
