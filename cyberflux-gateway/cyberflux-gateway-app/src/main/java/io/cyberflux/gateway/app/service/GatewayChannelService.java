package io.cyberflux.gateway.app.service;

import org.springframework.stereotype.Service;

import io.cyberflux.gateway.app.repository.EngineMemRepository;

@Service
public class GatewayChannelService extends AbstractRequestService {

    public GatewayChannelService(EngineMemRepository repository) {
        super(repository);
    }

}
