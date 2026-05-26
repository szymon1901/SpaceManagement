package com.space.management.domain;

import com.space.management.application.command.AddMissionCommand;
import com.space.management.application.command.AddRocketCommand;
import com.space.management.application.command.AssignMultipleRocketsCommand;
import com.space.management.application.command.AssignRocketCommand;
import com.space.management.application.command.ChangeMissionStatusCommand;
import com.space.management.application.command.ChangeRocketStatusCommand;
import com.space.management.application.command.DeleteMissionCommand;
import com.space.management.application.command.DeleteRocketCommand;
import com.space.management.application.command.SpaceXCommandUseCase;

class SpaceXCommandService implements SpaceXCommandUseCase {

    private final AddRocketUseCase addRocketUseCase;
    private final AddMissionUseCase addMissionUseCase;
    private final AssignRocketUseCase assignRocketUseCase;
    private final AssignMultipleRocketsUseCase assignMultipleRocketsUseCase;
    private final ChangeRocketStatusUseCase changeRocketStatusUseCase;
    private final ChangeMissionStatusUseCase changeMissionStatusUseCase;
    private final DeleteRocketUseCase deleteRocketUseCase;
    private final DeleteMissionUseCase deleteMissionUseCase;

    SpaceXCommandService(RocketCommandRepository rocketRepository, MissionCommandRepository missionRepository, SpaceXDomainMapper mapper) {
        this.addRocketUseCase = new AddRocketUseCase(rocketRepository, mapper);
        this.addMissionUseCase = new AddMissionUseCase(missionRepository, mapper);
        this.assignRocketUseCase = new AssignRocketUseCase(rocketRepository, missionRepository, mapper);
        this.assignMultipleRocketsUseCase = new AssignMultipleRocketsUseCase(rocketRepository, missionRepository, mapper);
        this.changeRocketStatusUseCase = new ChangeRocketStatusUseCase(rocketRepository, mapper);
        this.changeMissionStatusUseCase = new ChangeMissionStatusUseCase(missionRepository, mapper);
        this.deleteRocketUseCase = new DeleteRocketUseCase(rocketRepository, mapper);
        this.deleteMissionUseCase = new DeleteMissionUseCase(missionRepository, rocketRepository, mapper);
    }

    @Override
    public void handle(AddRocketCommand command) {
        addRocketUseCase.execute(command);
    }

    @Override
    public void handle(AddMissionCommand command) {
        addMissionUseCase.execute(command);
    }

    @Override
    public void handle(AssignRocketCommand command) {
        assignRocketUseCase.execute(command);
    }

    @Override
    public void handle(AssignMultipleRocketsCommand command) {
        assignMultipleRocketsUseCase.execute(command);
    }

    @Override
    public void handle(ChangeRocketStatusCommand command) {
        changeRocketStatusUseCase.execute(command);
    }

    @Override
    public void handle(ChangeMissionStatusCommand command) {
        changeMissionStatusUseCase.execute(command);
    }

    @Override
    public void handle(DeleteRocketCommand command) {
        deleteRocketUseCase.execute(command);
    }

    @Override
    public void handle(DeleteMissionCommand command) {
        deleteMissionUseCase.execute(command);
    }
}
