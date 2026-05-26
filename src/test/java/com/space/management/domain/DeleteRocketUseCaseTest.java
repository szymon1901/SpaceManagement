package com.space.management.domain;

import com.space.management.application.command.DeleteRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.common.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static com.space.management.domain.SpaceXTestDataFactory.createDeleteRocketCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteRocketUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldDeleteRocket_whenRocketExistsAndIsNotAssignedToAnyMission() {
        // given
        String rocketName = "Starship SN15";
        saveRocketInRepository(rocketName, ON_GROUND, null);

        DeleteRocketCommand command = createDeleteRocketCommand(rocketName);

        // when
        commandService.handle(command);

        // then
        assertRocketDoesNotExist(rocketName);
    }

    @Test
    void shouldThrowBusinessException_whenTryingToDeleteRocketAssignedToAMission() {
        // given
        String rocketName = "Falcon 9";
        String activeMission = "Starlink-99";
        saveRocketInRepository(rocketName, IN_SPACE, activeMission);

        DeleteRocketCommand command = createDeleteRocketCommand(rocketName);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.INVALID_MISSION_STATUS, exception.getErrorMessage());
        assertRocketStillExists(rocketName);
    }

    @Test
    void shouldThrowBusinessException_whenRocketDoesNotExist() {
        // given
        String nonExistingRocket = "UFO";
        DeleteRocketCommand command = createDeleteRocketCommand(nonExistingRocket);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.ROCKET_NOT_FOUND, exception.getErrorMessage());
    }

    private void assertRocketStillExists(String rocketName) {
        assertTrue(rocketRepository.findByName(rocketName).isPresent(),
            "Rocket should still exist in repository after failed deletion attempt.");
    }
}
