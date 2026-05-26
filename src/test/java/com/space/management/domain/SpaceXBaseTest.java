package com.space.management.domain;

import com.space.management.common.MissionStatus;
import com.space.management.common.RocketStatus;
import com.space.management.repository.MissionEntity;
import com.space.management.repository.RocketEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

import static com.space.management.domain.SpaceXTestDataFactory.createMissionEntity;
import static com.space.management.domain.SpaceXTestDataFactory.createRocketEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class SpaceXBaseTest {
    protected SpaceXDomainMapper mapper;
    protected RocketCommandRepository rocketRepository;
    protected MissionCommandRepository missionRepository;
    protected SpaceXCommandService commandService;

    @BeforeEach
    void setUp() {
        this.mapper = new SpaceXDomainMapper();

        this.rocketRepository = new TestInMemoryRocketCommandRepository();
        this.missionRepository = new TestInMemoryMissionCommandRepository();

        this.commandService = new SpaceXCommandService(rocketRepository, missionRepository, mapper);
    }

    protected void saveMissionInRepository(String name, MissionStatus status) {
        saveMissionInRepository(name, status, List.of());
    }

    protected void saveMissionInRepository(String name, MissionStatus status, List<String> rocketNames) {
        missionRepository.save(createMissionEntity(name, status, rocketNames));
    }

    protected void saveRocketInRepository(String name, RocketStatus status) {
        saveRocketInRepository(name, status, null);
    }

    protected void saveRocketInRepository(String name, RocketStatus status, String assignedMissionName) {
        rocketRepository.save(createRocketEntity(name, status, assignedMissionName));
    }

    protected void assertMissionExistsWithStatus(String missionName, MissionStatus expectedStatus) {
        MissionEntity mission = missionRepository.findByName(missionName)
            .orElseThrow(() -> new AssertionError("Mission '" + missionName + "' should exist."));
        assertEquals(expectedStatus, mission.getStatus());
    }

    protected void assertRocketExistsWithStatus(String rocketName, RocketStatus expectedStatus) {
        RocketEntity rocket = rocketRepository.findByName(rocketName)
            .orElseThrow(() -> new AssertionError("Rocket '" + rocketName + "' should exist."));
        assertEquals(expectedStatus, rocket.getStatus());
    }

    protected void assertRocketIsAssignedToMission(String rocketName, String expectedMissionName) {
        RocketEntity rocket = rocketRepository.findByName(rocketName).orElseThrow();
        assertEquals(expectedMissionName, rocket.getAssignedMissionName());
    }

    protected void assertMissionContainsRockets(String missionName, String... expectedRocketNames) {
        MissionEntity mission = missionRepository.findByName(missionName).orElseThrow();
        for (String rocketName : expectedRocketNames) {
            assertTrue(mission.getRocketNames().contains(rocketName));
        }
    }

    protected void assertMissionDoesNotExist(String missionName) {
        assertTrue(missionRepository.findByName(missionName).isEmpty(),
            "Mission '" + missionName + "' should be completely removed from repository.");
    }

    protected void assertRocketDoesNotExist(String rocketName) {
        assertTrue(rocketRepository.findByName(rocketName).isEmpty(),
            "Rocket '" + rocketName + "' should be completely removed from repository.");
    }
}
