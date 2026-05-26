package com.space.management.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpaceXDataStore {
    private final Map<String, RocketEntity> rocketsTable = new ConcurrentHashMap<>();
    private final Map<String, MissionEntity> missionsTable = new ConcurrentHashMap<>();

    public Map<String, RocketEntity> getRocketsTable() {
        return rocketsTable;
    }

    public Map<String, MissionEntity> getMissionsTable() {
        return missionsTable;
    }

    public void clear() {
        rocketsTable.clear();
        missionsTable.clear();
    }
}
