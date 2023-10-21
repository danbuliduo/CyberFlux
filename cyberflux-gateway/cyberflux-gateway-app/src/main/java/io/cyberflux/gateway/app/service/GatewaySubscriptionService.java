package io.cyberflux.gateway.app.service;

import org.springframework.stereotype.Service;

import io.cyberflux.gateway.app.repository.EngineMemRepository;

@Service
public class GatewaySubscriptionService extends AbstractRequestService {

    public GatewaySubscriptionService(EngineMemRepository repository) {
        super(repository);
    }

}
