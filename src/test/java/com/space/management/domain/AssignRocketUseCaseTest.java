package com.space.management.domain;

import com.space.management.application.command.AssignRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.common.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.ENDED;
import static com.space.management.common.MissionStatus.IN_PROGRESS;
import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignRocketUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldSuccessfullyLinkRocketAndMission_whenBothExistAndAreValid() {
        // given
        String rocketName = "Falcon 9";
        String missionName = "CRS-26";
        saveRocketInRepository(rocketName, ON_GROUND, null);
        saveMissionInRepository(missionName, SCHEDULED);

        AssignRocketCommand command = SpaceXTestDataFactory.createAssignRocketCommand(rocketName, missionName);

        // when
        commandService.handle(command);

        // then
        assertRocketIsAssignedToMission(rocketName, missionName);
        assertMissionContainsRockets(missionName, rocketName);
        assertMissionExistsWithStatus(missionName, IN_PROGRESS);
    }

    @Test
    void shouldThrowBusinessException_whenRocketIsAlreadyAssignedToAnotherMission() {
        // given
        String rocketName = "Falcon 9";
        String activeMission = "Starlink-1";
        String newMission = "Artemis-I";

        saveRocketInRepository(rocketName, IN_SPACE, activeMission);
        saveMissionInRepository(newMission, SCHEDULED);

        AssignRocketCommand command = SpaceXTestDataFactory.createAssignRocketCommand(rocketName, newMission);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.ROCKET_ALREADY_ASSIGNED, exception.getErrorMessage());
    }

    @Test
    void shouldThrowBusinessException_whenMissionStatusIsEnded() {
        // given
        String rocketName = "Falcon 9";
        String endedMission = "Apollo-11";

        saveRocketInRepository(rocketName, ON_GROUND, null);
        saveMissionInRepository(endedMission, ENDED);

        AssignRocketCommand command = SpaceXTestDataFactory.createAssignRocketCommand(rocketName, endedMission);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.INVALID_MISSION_STATUS, exception.getErrorMessage());
    }

    @Test
    void shouldThrowBusinessException_whenRocketDoesNotExist() {
        // given
        String missionName = "CRS-26";
        saveMissionInRepository(missionName, SCHEDULED);
        AssignRocketCommand command = SpaceXTestDataFactory.createAssignRocketCommand("Ghost Rocket", missionName);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.ROCKET_NOT_FOUND, exception.getErrorMessage());
    }
}
