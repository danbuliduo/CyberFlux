package io.cyberflux.gateway.app.service;

import io.cyberflux.engine.core.model.EngineModel;
import io.cyberflux.gateway.app.context.GatewaySendEventContext;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import io.cyberflux.engine.core.model.EngineRegisterMessage;
import io.cyberflux.gateway.app.codec.EngineEntity;
import io.cyberflux.gateway.app.codec.EngineEntity.Status;
import io.cyberflux.gateway.app.repository.EngineMemRepository;
import io.cyberflux.meta.ResponseCode;
import io.cyberflux.meta.ResponseResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GatewayEngineService extends AbstractRequestService {

	private final GatewaySendEventContext sendEventContext;

    public GatewayEngineService(EngineMemRepository repository) {
        super(repository);
		sendEventContext = GatewaySendEventContext.instance();
    }

    public Flux<EngineEntity> get() {
		return repository.findAll().onErrorResume(e -> Flux.empty());
	}

	public Mono<ResponseResult> register(EngineRegisterMessage message) {
		EngineModel model = message.getEngine();
		return repository.findByHost(model.getHost())
			.doOnNext(item-> {
				item.setStatus(Status.UP);
				repository.save(item);
				sendEventContext.emit(ServerSentEvent.builder(item).event("engine-link").build());
			})
			.map(item -> ResponseResult.codeResult(ResponseCode.ACCE))
			.onErrorResume(e -> Mono.empty());
	}
}
