package com.space.management.repository;

import com.space.management.common.RocketStatus;

public class RocketEntity {
    private String name;
    private RocketStatus status;
    private String assignedMissionName;

    public RocketEntity(String name, RocketStatus status, String assignedMissionName) {
        this.name = name;
        this.status = status;
        this.assignedMissionName = assignedMissionName;
    }

    public String getName() {
        return name;
    }

    public RocketStatus getStatus() {
        return status;
    }

    public String getAssignedMissionName() {
        return assignedMissionName;
    }
}
