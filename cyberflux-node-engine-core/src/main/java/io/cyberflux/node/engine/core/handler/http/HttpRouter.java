package io.cyberflux.node.engine.core.handler.http;

import org.reactivestreams.Publisher;

import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public interface HttpRouter {
	Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response);
}
