package com.space.management.domain;

import com.space.management.application.command.AddMissionCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.MissionEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_ALREADY_EXISTS;
import static com.space.management.domain.Mission.createNew;

class AddMissionUseCase {
    private final MissionCommandRepository missionRepository;
    private final SpaceXDomainMapper mapper;

    public AddMissionUseCase(MissionCommandRepository missionRepository, SpaceXDomainMapper mapper) {
        this.missionRepository = missionRepository;
        this.mapper = mapper;
    }

    void execute(AddMissionCommand command) {
        ensureMissionDoesNotExist(command.missionName());

        Mission newMission = createNew(command.missionName());

        saveMissionState(newMission);
    }

    private void ensureMissionDoesNotExist(String missionName) {
        missionRepository.findByName(missionName).ifPresent(mission -> {
            throw new BusinessValidationException(MISSION_ALREADY_EXISTS);
        });
    }

    private void saveMissionState(Mission mission) {
        MissionEntity updatedMissionEntity = mapper.toEntity(mission);
        missionRepository.save(updatedMissionEntity);
    }
}
