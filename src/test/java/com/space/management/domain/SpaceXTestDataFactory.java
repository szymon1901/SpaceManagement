package com.space.management.domain;

import com.space.management.application.command.AddMissionCommand;
import com.space.management.application.command.AddRocketCommand;
import com.space.management.application.command.AssignMultipleRocketsCommand;
import com.space.management.application.command.AssignRocketCommand;
import com.space.management.application.command.DeleteMissionCommand;
import com.space.management.application.command.DeleteRocketCommand;
import com.space.management.common.MissionStatus;
import com.space.management.common.RocketStatus;
import com.space.management.repository.MissionEntity;
import com.space.management.repository.RocketEntity;
import java.util.List;

class SpaceXTestDataFactory {

    static AddRocketCommand createAddRocketCommand(String name) {
        return new AddRocketCommand(name);
    }

    static RocketEntity createRocketEntity(String name, RocketStatus status, String missionName) {
        return new RocketEntity(name, status, missionName);
    }

    static AddMissionCommand createAddMissionCommand(String name) {
        return new AddMissionCommand(name);
    }

    static MissionEntity createMissionEntity(String name, MissionStatus status, List<String> rocketNames) {
        return new MissionEntity(name, status, rocketNames);
    }

    static AssignRocketCommand createAssignRocketCommand(String rocketName, String missionName) {
        return new AssignRocketCommand(rocketName, missionName);
    }

    static AssignMultipleRocketsCommand createAssignMultipleRocketsCommand(String missionName, String... rocketNames) {
        return new AssignMultipleRocketsCommand(missionName, List.of(rocketNames));
    }

    static DeleteRocketCommand createDeleteRocketCommand(String rocketName) {
        return new DeleteRocketCommand(rocketName);
    }

    static DeleteMissionCommand createDeleteMissionCommand(String missionName) {
        return new DeleteMissionCommand(missionName);
    }
}
