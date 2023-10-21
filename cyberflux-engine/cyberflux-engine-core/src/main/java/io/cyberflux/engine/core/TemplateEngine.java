package io.cyberflux.engine.core;

import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.utils.io.JsonLoaderUtils;
import io.cyberflux.utils.net.HostUtils;
import io.cyberflux.cluster.ClusterAdapter;
import io.cyberflux.cluster.ClusterMessageListener;
import io.cyberflux.meta.cluster.ClusterMessage;
import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.reactor.mqtt.MqttReactor;
import io.cyberflux.engine.core.config.EngineConfig;
import io.cyberflux.engine.core.config.GatewayConfig;
import io.cyberflux.engine.core.context.AppilcationContext;
import io.cyberflux.engine.core.model.EngineModel;
import io.cyberflux.engine.core.model.EngineRegisterMessage;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public abstract class TemplateEngine extends ClusterAdapter implements Engine {
    private static final Logger log = LoggerFactory.getLogger(TemplateEngine.class);
	private static final String VERSION = "META";
	protected final Map<String, Reactor> reactors;
	protected final AppilcationContext context;

	public TemplateEngine(EngineConfig config) {
		super(config.getCluster());
		this.reactors = new ConcurrentHashMap<>();
		this.context = new AppilcationContext(config);
		context.config().getReactor().getMqttOptions().forEach(option -> {
			this.appendReactor(new MqttReactor(option));
		});
		this.addMessageListener(message -> {
			findReactor().subscribe(reactor -> {
				reactor.publisher().publish(message).subscribe();
			});
		});
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownReactor));
	}

	public void doRegisterGateway()  {
		GatewayConfig gatewayConfig = context.config().getGateway();
		if (gatewayConfig.isEnable()) {
			HttpClient http = HttpClient.create().headers(h -> h.add(
				HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
			);
			EngineRegisterMessage message = new EngineRegisterMessage(
				"", this.profile()
			);
			new Timer().scheduleAtFixedRate(new TimerTask() {
				int count = 0;
				final int retries = gatewayConfig.getMaxRetries();
				@Override
				public void run() {
					if(count++ < retries) {
						http.post().uri(gatewayConfig.getUri())
							.send(ByteBufFlux.fromString(
								Mono.just(JsonLoaderUtils.toJsonString(message))
							))
							.responseSingle((response, bytes) -> {
								if (response.status() == HttpResponseStatus.OK) {
									return bytes.asString();
								}
								throw new RuntimeException("Register Gateway Failed!");
							})
							.subscribe(body -> {
								this.cancel();
							});
					} else {
						this.cancel();
					}
				}
			}, 0, gatewayConfig.getRetryInterval().toMillis());
		}
	}



	public AppilcationContext context() {
		return this.context;
	}

	@Override
	public String version() {
		return VERSION;
	}

	public String host() {
		return HostUtils.getLocalIp() + ":" + context().config().getCluster().getPort();
	}

	public String mode() {
		return context.config().getCluster().getMode();
	}

	public String name() {
		return context.config().getCluster().getName();
	}

	public String apiPath() {
		return "http://" + HostUtils.getLocalIp() + ":" +
			context().config().getApiPort() + context().config().getApiPath();
	}

	public EngineModel profile() {
		return EngineModel.builder()
			.id(id())
			.mode(mode())
			.name(name())
			.host(host())
			.version(version())
			.apiPath(apiPath())
			.build();
	}

    @Override
    public Flux<Reactor> findReactor() {
        return Flux.fromIterable(reactors.values());
    }

    @Override
    public Flux<Reactor> findReactor(MetaType type) {
        return findReactor().filter(reactor -> type == reactor.type());
    }

    @Override
    public Flux<Reactor> findReactor(Collection<String> uuids) {
        return Flux.fromIterable(uuids).flatMap(uuid -> Flux.just(reactors.get(uuid)));
    }

    @Override
    public Mono<Reactor> findReactor(String uuid) {
        return Mono.just(reactors.get(uuid));
    }

    @Override
    public void appendReactor(Reactor reactor) {
       	reactors.put(reactor.uuid(), reactor);
    }

    @Override
    public void appendReactor(Collection<Reactor> reactors) {
        reactors.forEach(reactor -> {
			this.reactors.put(reactor.uuid(), reactor);
		});
    }

    @Override
    public void removeReactor() {
        findReactor().subscribe(reactor -> reactors.remove(reactor.uuid()));
    }

    @Override
    public void removeReactor(MetaType type) {
        findReactor(type).subscribe(reactor -> reactors.remove(reactor.uuid()));
    }

    @Override
    public void removeReactor(Collection<String> uuids) {
        Flux.fromIterable(uuids).subscribe(reactors::remove);
    }

    @Override
    public void removeReactor(String uuid) {
       	reactors.remove(uuid);
    }

    @Override
    public void startReactor() {
        log.info("NodeEngine::StartAll => [count:{}]", reactors.size());
        findReactor().subscribe(this::startReactor);
    }

    @Override
    public void startReactor(MetaType type) {
        findReactor(type).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(Collection<String> uuids) {
        findReactor(uuids).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(String uuid) {
        findReactor(uuid).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(Reactor reactor) {
        reactor.startAwait();
		reactor.receiver().receive()
			.doOnError(throwable -> log.error("{}", throwable))
			.onErrorResume(throwable -> Mono.empty())
			.subscribe(message -> {
				this.spreadMessage(message).subscribe();
			});
    }

    @Override
    public void shutdownReactor() {
        findReactor().subscribe(this::shutdownReactor);
        log.info("ShutdownAll => [count:{}]", reactors.size());
    }

    @Override
    public void shutdownReactor(MetaType type) {
        findReactor(type).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(Collection<String> uuids) {
        findReactor(uuids).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(String uuid) {
        findReactor(uuid).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(Reactor reactor) {
        reactor.shutdown();
    }



}
