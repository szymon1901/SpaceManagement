package com.space.management.domain;

import com.space.management.application.command.ChangeRocketStatusCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.common.exception.ErrorMessage;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.IN_PROGRESS;
import static com.space.management.common.MissionStatus.PENDING;
import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.RocketStatus.IN_REPAIR;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeRocketStatusUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldUpdateRocketStatusAndTriggerMissionInProgress_whenRocketIsAssignedToMission() {
        // given
        String missionName = "Apollo-11";
        String rocketName = "Saturn-V";

        saveMissionInRepository(missionName, SCHEDULED, List.of(rocketName));
        saveRocketInRepository(rocketName, ON_GROUND, missionName);

        ChangeRocketStatusCommand command = new ChangeRocketStatusCommand(rocketName, IN_SPACE);

        // when
        commandService.handle(command);

        // then
        assertRocketExistsWithStatus(rocketName, IN_SPACE);
        assertMissionExistsWithStatus(missionName, IN_PROGRESS);
    }

    @Test
    void shouldTriggerMissionPendingStatus_whenAssignedRocketEntersRepair() {
        // given
        String missionName = "Project-Artemis";
        String rocketA = "Orion-1";
        String rocketB = "Orion-2";

        saveMissionInRepository(missionName, IN_PROGRESS, of(rocketA, rocketB));
        saveRocketInRepository(rocketA, IN_SPACE, missionName);
        saveRocketInRepository(rocketB, IN_SPACE, missionName);

        ChangeRocketStatusCommand command = new ChangeRocketStatusCommand(rocketB, IN_REPAIR);

        // when
        commandService.handle(command);

        // then
        assertRocketExistsWithStatus(rocketB, IN_REPAIR);
        assertMissionExistsWithStatus(missionName, PENDING);
    }

    @Test
    void shouldThrowBusinessException_whenRocketDoesNotExist() {
        // given
        String nonExistingRocket = "Vostok 1";
        ChangeRocketStatusCommand command = new ChangeRocketStatusCommand(nonExistingRocket, IN_SPACE);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.ROCKET_NOT_FOUND, exception.getErrorMessage());
    }
}
