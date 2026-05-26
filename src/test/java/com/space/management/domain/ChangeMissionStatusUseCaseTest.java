package com.space.management.domain;

import com.space.management.application.command.ChangeMissionStatusCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.common.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

import static com.space.management.common.MissionStatus.ENDED;
import static com.space.management.common.MissionStatus.IN_PROGRESS;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeMissionStatusUseCaseTest extends SpaceXBaseTest {
    @Test
    void shouldUpdateMissionStatusAndReleaseAllRockets_whenMissionIsEnded() {
        // given
        String missionName = "Falcon Eye";
        String rocket1 = "Falcon-Heavy-A";
        String rocket2 = "Falcon-Heavy-B";

        saveMissionInRepository(missionName, IN_PROGRESS, of(rocket1, rocket2));
        saveRocketInRepository(rocket1, IN_SPACE, missionName);
        saveRocketInRepository(rocket2, IN_SPACE, missionName);

        ChangeMissionStatusCommand command = new ChangeMissionStatusCommand(missionName, ENDED);

        // when
        commandService.handle(command);

        // then
        assertMissionExistsWithStatus(missionName, ENDED);

        // PROAKTYWNA WERYFIKACJA KASKADY BIZNESOWEJ: Rakiety muszą wrócić na ziemię wolne!
        assertRocketExistsWithStatus(rocket1, ON_GROUND);
        assertRocketExistsWithStatus(rocket2, ON_GROUND);
    }

    @Test
    void shouldThrowBusinessException_whenMissionDoesNotExist() {
        // given
        String nonExistingMission = "Secret Soviet Mission";
        ChangeMissionStatusCommand command = new ChangeMissionStatusCommand(nonExistingMission, IN_PROGRESS);

        // when & then
        BusinessValidationException exception = assertThrows(
            BusinessValidationException.class,
            () -> commandService.handle(command)
        );

        assertEquals(ErrorMessage.MISSION_NOT_FOUND, exception.getErrorMessage());
    }
}
