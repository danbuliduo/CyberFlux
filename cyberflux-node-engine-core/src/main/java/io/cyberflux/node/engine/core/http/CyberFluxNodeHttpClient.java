package io.cyberflux.node.engine.core.http;


import io.cyberflux.common.utils.CyberJsonUtils;
import io.cyberflux.meta.models.node.NodeEngineModel;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.logging.HttpMessageType;

public class CyberFluxNodeHttpClient {

	private HttpClient client;

	public CyberFluxNodeHttpClient() {
		client = HttpClient.create();
		register();
	}

	public void register() {
		NodeEngineModel e = new NodeEngineModel("f", "a", "c");

		System.out.println(CyberJsonUtils.toJsonString(e));

		client.headers(h -> h.add(HttpHeaderNames.CONTENT_TYPE, "application/json")).post()
			.uri("http://127.0.0.1:18087/node/register")
			.send(ByteBufFlux.fromString(
				Mono.just(CyberJsonUtils.toJsonString(e))
			)).responseSingle((response, bytes) -> {
				if(response.status() == HttpResponseStatus.OK) {
					return bytes.asString();
				}
				throw new RuntimeException("HTTP POST Failed!");
			}).subscribe(body -> {
				System.out.println(body);
			});
	}
}
