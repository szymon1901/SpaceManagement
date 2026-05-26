package com.space.management.domain;

import com.space.management.application.command.ChangeRocketStatusCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.RocketEntity;

import static com.space.management.common.exception.ErrorMessage.ROCKET_NOT_FOUND;

class ChangeRocketStatusUseCase {
    private final RocketCommandRepository rocketRepository;
    private final SpaceXDomainMapper mapper;

    ChangeRocketStatusUseCase(RocketCommandRepository rocketRepository, SpaceXDomainMapper mapper) {
        this.rocketRepository = rocketRepository;
        this.mapper = mapper;
    }

    void execute(ChangeRocketStatusCommand command) {
        Rocket rocket = loadRocket(command.rocketName());

        rocket.changeStatus(command.newStatus());

        saveRocketState(rocket);
    }

    private Rocket loadRocket(String rocketName) {
        return rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(ROCKET_NOT_FOUND));
    }

    private void saveRocketState(Rocket rocket) {
        RocketEntity updatedRocketEntity = mapper.toEntity(rocket);
        rocketRepository.save(updatedRocketEntity);
    }
}
