package io.cyberflux.node.engine.core.handler.http.router.collect;

import org.reactivestreams.Publisher;

import io.cyberflux.node.engine.core.annotation.RequestRouter;
import io.cyberflux.node.engine.core.context.NodeEngineSpiLoaderContext;
import io.cyberflux.node.engine.core.annotation.RequestHeader;
import io.cyberflux.node.engine.core.handler.http.HttpHeaderNames;
import io.cyberflux.node.engine.core.handler.http.HttpHraderValues;
import io.cyberflux.node.engine.core.handler.http.HttpMethods;
import io.cyberflux.node.engine.core.handler.http.HttpRouter;
import io.cyberflux.node.engine.core.utils.NodeEngineReactorGroup;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

@RequestRouter(path = "/collect/deivces/connection", method = HttpMethods.POST)
@RequestHeader(name = HttpHeaderNames.CONTENT_TYPE, value = HttpHraderValues.APPLICATION_JSON)
public class DevicesConnectionRouter implements HttpRouter {

	@Override
	public Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response) {
		return request.receive().then(
			//NodeEngineSpiLoaderContext.findFirst(NodeEngineReactorGroup.class).get();
			response.sendString(Mono.just("Test")).then()
		);
	}

}
