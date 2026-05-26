package com.space.management.repository;

import com.space.management.common.MissionStatus;
import java.util.ArrayList;
import java.util.List;

public class MissionEntity {
    private String name;
    private MissionStatus status;
    private List<String> rocketNames;

    public MissionEntity(String name, MissionStatus status, List<String> rocketNames) {
        this.name = name;
        this.status = status;
        this.rocketNames = rocketNames != null ? List.copyOf(rocketNames) : List.of();
    }

    public String getName() {
        return name;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public List<String> getRocketNames() {
        return rocketNames;
    }
}
