package com.space.management.domain;

import com.space.management.application.model.MissionModel;
import com.space.management.application.query.SpaceXQueryUseCase;
import java.util.List;

class SpaceXQueryService implements SpaceXQueryUseCase {
    private final SpaceXQueryRepository queryRepository;

    public SpaceXQueryService(SpaceXQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<MissionModel> getMissionsSummary() {
        return queryRepository.getMissionsSummary();
    }
}
