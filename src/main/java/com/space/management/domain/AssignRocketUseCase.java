package com.space.management.domain;

import com.space.management.application.command.AssignRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import java.util.ArrayList;
import java.util.List;

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

        List<Rocket> missionRockets = loadRocketsForMission(mission);
        mission.updateStatusBasedOnRockets(missionRockets);

        saveRocketState(rocket);
        saveMissionState(mission);
    }

    private List<Rocket> loadRocketsForMission(Mission mission) {
        List<Rocket> rockets = new ArrayList<>();
        for (String name : mission.getAssignedRocketNames()) {
            rocketRepository.findByName(name)
                .map(mapper::toDomain)
                .ifPresent(rockets::add);
        }
        return rockets;
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
        rocketRepository.save(mapper.toEntity(rocket));
    }

    private void saveMissionState(Mission mission) {
        missionRepository.save(mapper.toEntity(mission));
    }
}
