package com.space.management.repository;

import com.space.management.application.model.MissionModel;
import com.space.management.application.model.RocketModel;
import com.space.management.domain.SpaceXQueryRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

class InMemorySpaceXQueryRepository implements SpaceXQueryRepository {
    private final SpaceXDataStore dataStore;

    InMemorySpaceXQueryRepository(SpaceXDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<MissionModel> getMissionsSummary() {
        return dataStore.getMissionsTable().values().stream()
            .map(this::mapToMissionModel)
            .sorted(createMissionSummaryComparator())
            .toList();
    }

    private MissionModel mapToMissionModel(MissionEntity missionEntity) {
        List<RocketModel> rockets = missionEntity.getRocketNames().stream()
            .map(rocketName -> dataStore.getRocketsTable().get(rocketName))
            .filter(Objects::nonNull)
            .map(rocketEntity -> new RocketModel(rocketEntity.getName(), rocketEntity.getStatus()))
            .toList();

        return new MissionModel(
            missionEntity.getName(),
            missionEntity.getStatus(),
            rockets
        );
    }

    private Comparator<MissionModel> createMissionSummaryComparator() {
        return comparing((MissionModel m) -> m.rockets().size()).reversed()
            .thenComparing(MissionModel::name, reverseOrder());
    }
}
