package com.space.management.domain;

import com.space.management.application.command.AddRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.RocketEntity;
import org.junit.jupiter.api.Test;

import static com.space.management.common.RocketStatus.ON_GROUND;
import static com.space.management.common.exception.ErrorMessage.ROCKET_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddRocketUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldCreateAndSaveRocketWithOnGroundStatus_whenRocketNameIsUnique() {
        // given
        String uniqueRocketName = "Starship SN24";
        AddRocketCommand command = SpaceXTestDataFactory.createAddRocketCommand(uniqueRocketName);

        // when
        commandService.handle(command);

        // then
        assertRocketExistsWithStatus(uniqueRocketName, ON_GROUND);
        assertRocketIsNotAssignedToAnyMission(uniqueRocketName);
    }

    @Test
    void shouldThrowBusinessException_whenRocketWithGivenNameAlreadyExists() {
        // given
        String existingRocketName = "Falcon Heavy";
        saveRocketInRepository(existingRocketName, ON_GROUND, null);
        AddRocketCommand command = SpaceXTestDataFactory.createAddRocketCommand(existingRocketName);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ROCKET_ALREADY_EXISTS, exception.getErrorMessage());
    }

    private void assertRocketIsNotAssignedToAnyMission(String rocketName) {
        RocketEntity rocket = rocketRepository.findByName(rocketName).orElseThrow();
        assertNull(rocket.getAssignedMissionName(), "Newly created rocket should not be assigned to any mission.");
    }
}
