package io.cyberflux.cluster;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.cluster.ClusterMessage;
import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import io.scalecube.net.Address;
import io.scalecube.reactor.RetryNonSerializedEmitFailureHandler;
import io.scalecube.transport.netty.tcp.TcpTransportFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public abstract class ClusterAdapter implements io.cyberflux.cluster.Cluster {
	private static final Logger log = LoggerFactory.getLogger(ClusterAdapter.class);

	protected Cluster cluster;
	protected List<ClusterMemberEventListener> eventListeners;
	protected List<ClusterMessageListener> messageListeners;
	protected Sinks.Many<MembershipEvent> eventSinks;
	protected Sinks.Many<ClusterMessage> messageSinks;


    public ClusterAdapter(ClusterOption config) {
		this.messageListeners = new LinkedList<>();
		this.eventListeners = new LinkedList<>();
		if(config != null && config.enable) {
			this.eventSinks = Sinks.many().multicast().onBackpressureBuffer();
		    this.messageSinks = Sinks.many().multicast().onBackpressureBuffer();
			eventSinks.asFlux().subscribe(event -> {
				Flux.fromIterable(eventListeners).subscribe(listener -> listener.handle(event));
			});
			messageSinks.asFlux().subscribe(message -> {
				Flux.fromIterable(messageListeners).subscribe(listener -> listener.handle(message));
			});
			this.cluster = new ClusterImpl()
				.config(conf -> conf.memberAlias(config.getName()))
				.transportFactory(TcpTransportFactory::new)
				.transport(transport -> transport.port(config.getPort()))
				.membership(opts ->
					opts.seedMembers(
						Arrays.stream(config.getNodes()).map(Address::from).collect(Collectors.toList())
					).namespace(config.getNamespace())
				)
				.handler(cluster -> new ClusterHandler())
				.startAwait();
		}
    }

	public void addEventListener(ClusterMemberEventListener listener) {
		eventListeners.add(listener);
	}

	public void addMessageListener(ClusterMessageListener listener) {
		messageListeners.add(listener);
	}

	@Override
	public String id() {
		return cluster.member().id();
	}

    @Override
    public ClusterMember member(String name) {
       	return Optional.ofNullable(cluster).map(cluster ->
			cluster.member(name).map(member ->
				ClusterMember.builder()
					.id(member.id())
					.name(member.alias())
					.host(member.address().host())
					.port(member.address().port())
					.namespace(member.namespace())
					.build()
			).get()
		).get();

    }

    @Override
    public List<ClusterMember> members() {
        return Optional.ofNullable(cluster).map(cluster ->
			cluster.members().stream().map(member->
				ClusterMember.builder()
					.id(member.id())
					.name(member.alias())
					.host(member.address().host())
					.port(member.address().port())
					.namespace(member.namespace())
					.build()
			).collect(Collectors.toList())
		).orElse(Collections.emptyList());
    }

	@Override
	public Mono<Void> spreadMessage(ClusterMessage message) {
		return Mono.when(
			cluster.otherMembers().stream().map(member ->
				Optional.ofNullable(cluster).map(cluster ->
					cluster.send(member, Message.fromData(message)).then()
				).orElse(Mono.empty())
			).collect(Collectors.toList())
		);
	}

	@Override
	public Mono<Void> spreadMessage(ClusterMessage message, String name) {
		return Mono.when(
			cluster.member(name).map(member ->
				Optional.ofNullable(cluster).map(cluster ->
				 	cluster.send(member, Message.fromData(message)).then()
				).orElse(Mono.empty())
			).get()
		);
	}

	@Override
	public Mono<Void> shutdown() {
		return Mono.fromRunnable(() ->
			Optional.ofNullable(cluster).ifPresent(Cluster::shutdown)
		);
	}

	class ClusterHandler implements ClusterMessageHandler {

		@Override
		public void onMessage(Message message) {
			log.info("Message: {} ", message);
			messageSinks.emitNext(message.data(), new RetryNonSerializedEmitFailureHandler());
		}

		@Override
		public void onGossip(Message message) {
			log.info("Message: {} ", message);
			messageSinks.emitNext(message.data(), new RetryNonSerializedEmitFailureHandler());
		}

		@Override
		public void onMembershipEvent(MembershipEvent event) {
			log.info("Event: {} ", event);
			eventSinks.tryEmitNext(event);
		}
	}
}
