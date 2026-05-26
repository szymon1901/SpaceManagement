package com.space.management.application.command;

public interface SpaceXCommandUseCase {
    void handle(AddRocketCommand command);

    void handle(AddMissionCommand command);

    void handle(AssignRocketCommand command);

    void handle(AssignMultipleRocketsCommand command);

    void handle(ChangeRocketStatusCommand command);

    void handle(ChangeMissionStatusCommand command);

    void handle(DeleteMissionCommand command);

    void handle(DeleteRocketCommand command);
}
