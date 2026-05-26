package com.space.management.domain;

import com.space.management.application.command.DeleteMissionCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.RocketEntity;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static com.space.management.common.exception.ErrorMessage.MISSION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteMissionUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldDeleteMissionAndReleaseAllAssignedRockets_whenMissionExists() {
        // given
        String missionName = "CRS-28";
        String rocketName1 = "Falcon-9-Alpha";
        String rocketName2 = "Falcon-9-Beta";

        saveMissionInRepository(missionName, SCHEDULED, List.of(rocketName1, rocketName2));
        saveRocketInRepository(rocketName1, IN_SPACE, missionName);
        saveRocketInRepository(rocketName2, IN_SPACE, missionName);

        DeleteMissionCommand command = SpaceXTestDataFactory.createDeleteMissionCommand(missionName);

        // when
        commandService.handle(command);

        // then
        assertMissionDoesNotExist(missionName);
        assertRocketIsReleasedCorrectly(rocketName1);
        assertRocketIsReleasedCorrectly(rocketName2);
    }

    @Test
    void shouldThrowBusinessException_whenMissionDoesNotExist() {
        // given
        String nonExistingMission = "Ghost Mission 404";
        DeleteMissionCommand command = SpaceXTestDataFactory.createDeleteMissionCommand(nonExistingMission);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(MISSION_NOT_FOUND, exception.getErrorMessage());
    }

    private void assertRocketIsReleasedCorrectly(String rocketName) {
        RocketEntity rocket = rocketRepository.findByName(rocketName).orElseThrow();
        assertNull(rocket.getAssignedMissionName(), "Rocket " + rocketName + " should have its mission cleared.");
        assertEquals(ON_GROUND, rocket.getStatus(), "Rocket " + rocketName + " should back to ON_GROUND status.");
    }
}
