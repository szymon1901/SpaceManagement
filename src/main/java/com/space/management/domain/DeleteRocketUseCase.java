package com.space.management.domain;

import com.space.management.application.command.DeleteRocketCommand;
import com.space.management.common.exception.BusinessValidationException;

import static com.space.management.common.exception.ErrorMessage.ROCKET_NOT_FOUND;

class DeleteRocketUseCase {
    private final RocketCommandRepository rocketRepository;
    private final SpaceXDomainMapper mapper;

    DeleteRocketUseCase(RocketCommandRepository rocketRepository, SpaceXDomainMapper mapper) {
        this.rocketRepository = rocketRepository;
        this.mapper = mapper;
    }

    void execute(DeleteRocketCommand command) {
        Rocket rocket = loadRocket(command.rocketName());

        rocket.verifyCanBeDeleted();

        rocketRepository.delete(command.rocketName());
    }

    private Rocket loadRocket(String rocketName) {
        return rocketRepository.findByName(rocketName)
            .map(mapper::toDomain)
            .orElseThrow(() -> new BusinessValidationException(ROCKET_NOT_FOUND));
    }
}
