package com.space.management.domain;

import com.space.management.application.command.ChangeMissionStatusCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.MissionEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;

class ChangeMissionStatusUseCase {
    private final MissionCommandRepository missionRepository;
    private final SpaceXDomainMapper mapper;

    ChangeMissionStatusUseCase(MissionCommandRepository missionRepository, SpaceXDomainMapper mapper) {
        this.missionRepository = missionRepository;
        this.mapper = mapper;
    }

    void execute(ChangeMissionStatusCommand command) {
        Mission mission = loadMission(command.missionName());

        mission.changeStatus(command.newStatus());

        saveMissionState(mission);
    }

    private Mission loadMission(String missionName) {
        return missionRepository.findByName(missionName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(MISSION_NOT_FOUND));
    }

    private void saveMissionState(Mission mission) {
        MissionEntity updatedMissionEntity = mapper.toEntity(mission);
        missionRepository.save(updatedMissionEntity);
    }
}
