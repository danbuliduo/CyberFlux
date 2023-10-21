package io.cyberflux.gateway.app.service;

import io.cyberflux.gateway.app.codec.EngineEntity.Status;
import io.cyberflux.gateway.app.repository.EngineMemRepository;
import io.cyberflux.utils.io.JsonLoaderUtils;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public abstract class AbstractRequestService {

    protected EngineMemRepository repository;

    public AbstractRequestService(EngineMemRepository repository) {
        this.repository = repository;
    }

    public Flux<?> request(HttpMethod method, String suffix) {
        HttpClient clients = HttpClient.create();
        return repository.findAll()
            .filter(item -> item.getStatus() == Status.UP)
            .flatMap(item -> {
                return Mono.just(item.getId()).zipWith(
                    clients.request(method)
                        .uri(item.getApiPath() + suffix)
                        .responseSingle((response, bytes) -> {
						    if (response.status() == HttpResponseStatus.OK) {
							    return bytes.asString();
						    }
					        throw new RuntimeException();
					    }).map(i -> JsonLoaderUtils.toObject(i,Object.class))
                );
            })
            .onErrorResume(e -> Flux.empty());
    }

    public Mono<?> request(String id, HttpMethod method, String suffix) {
        HttpClient clients = HttpClient.create();
        return repository.findById(id)
            .filter(item -> item.getStatus() == Status.UP)
            .flatMap(item -> {
                return clients.request(method)
                    .uri(item.getApiPath() + suffix)
                    .responseSingle((response, bytes) -> {
						if (response.status() == HttpResponseStatus.OK) {
							return bytes.asString();
						}
					    throw new RuntimeException();
				    }).map(i -> JsonLoaderUtils.toObject(i,Object.class));
            }).onErrorResume(e -> Mono.empty());
    }

}
