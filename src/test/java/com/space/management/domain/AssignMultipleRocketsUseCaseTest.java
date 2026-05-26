package com.space.management.domain;

import com.space.management.application.command.AssignMultipleRocketsCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.common.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.IN_PROGRESS;
import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignMultipleRocketsUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldAssignAllRocketsToMission_whenMissionAndAllRocketsAreValidAndFree() {
        // given
        String missionName = "Transporter-6";
        saveMissionInRepository(missionName, SCHEDULED);
        saveRocketInRepository("Orbiter-A", ON_GROUND, null);
        saveRocketInRepository("Orbiter-B", ON_GROUND, null);

        AssignMultipleRocketsCommand command = SpaceXTestDataFactory.createAssignMultipleRocketsCommand(
            missionName, "Orbiter-A", "Orbiter-B"
        );

        // when
        commandService.handle(command);

        // then
        assertMissionContainsRockets(missionName, "Orbiter-A", "Orbiter-B");
        assertRocketIsAssignedToMission("Orbiter-A", missionName);
        assertRocketIsAssignedToMission("Orbiter-B", missionName);
        assertMissionExistsWithStatus(missionName, IN_PROGRESS);
    }

    @Test
    void shouldThrowBusinessException_whenOneOfGivenRocketsIsAlreadyAssignedToAnotherMission() {
        // given
        String missionName = "Transporter-6";
        saveMissionInRepository(missionName, SCHEDULED);

        saveRocketInRepository("Free-Rocket", ON_GROUND, null);
        saveRocketInRepository("Busy-Rocket", IN_SPACE, "Other-Active-Mission");

        AssignMultipleRocketsCommand command = SpaceXTestDataFactory.createAssignMultipleRocketsCommand(
            missionName, "Free-Rocket", "Busy-Rocket"
        );

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.ROCKET_ALREADY_ASSIGNED, exception.getErrorMessage());
    }
}
