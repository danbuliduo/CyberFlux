package io.cyberflux.node.engine.core.handler.http;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;

import io.cyberflux.node.engine.core.annotation.RequestHeader;
import io.cyberflux.node.engine.core.annotation.RequestRouter;
import io.cyberflux.node.engine.core.context.NodeEngineSpiLoaderContext;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerRoutes;

public class HttpRouterAcceptor implements Consumer<HttpServerRoutes>{

	@Override
	public void accept(HttpServerRoutes httpServerRoutes) {
		NodeEngineSpiLoaderContext.findAll(HttpRouter.class).collect(Collectors.toList()).forEach(router -> {
			RequestRouter requestRouter = router.getClass().getAnnotation(RequestRouter.class);
			BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> handler = (request , response) -> {
				RequestHeader requestHeader = router.getClass().getAnnotation(RequestHeader.class);
				if(requestHeader != null) {
					response.addHeader(requestHeader.name(), requestHeader.value());
				}
				return router.handle(request, response);
			};
			switch (requestRouter.method()) {
				case GET -> httpServerRoutes.get(requestRouter.path(), handler);
				case PUT -> httpServerRoutes.put(requestRouter.path(), handler);
				case POST -> httpServerRoutes.post(requestRouter.path(), handler);
				case DELETE -> httpServerRoutes.delete(requestRouter.path(), handler);
				case OPTIONS -> httpServerRoutes.options(requestRouter.path(), handler);
				default -> httpServerRoutes.get(requestRouter.path(), handler);
			}
		});
	}
}
