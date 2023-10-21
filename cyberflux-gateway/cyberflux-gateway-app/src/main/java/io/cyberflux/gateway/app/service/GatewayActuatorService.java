package io.cyberflux.gateway.app.service;

import org.springframework.stereotype.Service;


import io.cyberflux.gateway.app.repository.EngineMemRepository;

@Service
public class GatewayActuatorService extends AbstractRequestService {

    public GatewayActuatorService(EngineMemRepository repository) {
        super(repository);
    }

}
