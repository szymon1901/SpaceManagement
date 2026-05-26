package com.space.management.domain;

import com.space.management.application.command.AddMissionCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.MissionEntity;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.exception.ErrorMessage.MISSION_ALREADY_EXISTS;
import static com.space.management.domain.SpaceXTestDataFactory.createAddMissionCommand;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddMissionUseCaseTest extends SpaceXBaseTest {

    @Test
    void shouldCreateAndSaveMissionWithScheduledStatus_whenMissionNameIsUnique() {
        // given
        String uniqueMissionName = "Artemis III";
        AddMissionCommand command = createAddMissionCommand(uniqueMissionName);

        // when
        commandService.handle(command);

        // then
        assertMissionExistsWithStatus(uniqueMissionName, SCHEDULED);
        assertMissionHasNoAssignedRockets(uniqueMissionName);
    }

    @Test
    void shouldThrowBusinessException_whenMissionWithGivenNameAlreadyExists() {
        // given
        String existingMissionName = "Apollo 11";
        saveMissionInRepository(existingMissionName, SCHEDULED, emptyList());
        AddMissionCommand command = createAddMissionCommand(existingMissionName);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(MISSION_ALREADY_EXISTS, exception.getErrorMessage());
    }

    private void assertMissionHasNoAssignedRockets(String missionName) {
        MissionEntity mission = missionRepository.findByName(missionName).orElseThrow();
        assertNotNull(mission.getRocketNames());
        assertTrue(mission.getRocketNames().isEmpty(), "Newly created mission should not have any assigned rockets.");
    }
}
