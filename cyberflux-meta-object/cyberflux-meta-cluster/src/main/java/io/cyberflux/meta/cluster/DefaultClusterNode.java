package io.cyberflux.meta.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.data.CyberClusterEvent;
import io.cyberflux.meta.data.CyberClusterMessage;
import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.Member;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import io.scalecube.net.Address;
import io.scalecube.reactor.RetryNonSerializedEmitFailureHandler;
import io.scalecube.transport.netty.tcp.TcpTransportFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


public abstract class DefaultClusterNode extends CyberObject implements CyberClusterNode {
	private static final Logger log = LoggerFactory.getLogger(DefaultClusterNode.class);
	protected Cluster cluster;
	protected EnumSet<CyberType> types;
	protected Sinks.Many<CyberClusterEvent> eventSinks;
	protected Sinks.Many<CyberClusterMessage> messageSinks;

    public DefaultClusterNode(CyberClusterConfig config) {
        this(config, EnumSet.allOf(CyberType.class));
    }

    public DefaultClusterNode(CyberClusterConfig config, CyberType... types) {
        this(config, Arrays.stream(types).toList());
    }

    public DefaultClusterNode(CyberClusterConfig config, Collection<CyberType> types) {
        this(config, EnumSet.copyOf(types));
    }

    public DefaultClusterNode(CyberClusterConfig config, EnumSet<CyberType> types) {
		super(CyberType.GOSSIP);
        this.types = EnumSet.copyOf(types);
		this.eventSinks = Sinks.many().multicast().onBackpressureBuffer();
		this.messageSinks = Sinks.many().multicast().onBackpressureBuffer();
		this.registryClusterNode(config);
    }

	public DefaultClusterNode registryClusterNode(CyberClusterConfig config) {
		if(config != null && config.enable) {
			this.cluster = new ClusterImpl()
				.transportFactory(TcpTransportFactory::new)
				.transport(transport -> transport.port(config.getPort()))
				.membership(opts -> opts.seedMembers(
					Arrays.stream(config.getNodes().split(","))
						.map(Address::from)
						.collect(Collectors.toList())
				).namespace(config.getNamespace()))
				.handler(cluster -> new ClusterHandler())
				.startAwait();
			this.registryMessageHandler();
			this.registryEventHandler();
		}
		return this;
	}

	public void registryMessageHandler() {
		// do
	}

	public void registryEventHandler() {
		// do
	}

	@Override
	public Flux<CyberClusterMessage> receiveMessage() {
		return messageSinks.asFlux();
	}

	@Override
	public Flux<CyberClusterEvent> receiveEvent() {
		return eventSinks.asFlux();
	}

    @Override
    public CyberClusterMember member(String name) {
       	return Optional.ofNullable(cluster).map(cluster ->
			cluster.member(name).map(member ->
				CyberClusterMember.builder()
					.id(member.id())
					.alias(member.alias())
					.host(member.address().host())
					.port(member.address().port())
					.namespace(member.namespace())
					.build()
			).get()
		).get();

    }

    @Override
    public List<CyberClusterMember> members() {
        return Optional.ofNullable(cluster).map(cluster ->
			cluster.members().stream().map(member->
				CyberClusterMember.builder()
					.id(member.id())
					.alias(member.alias())
					.host(member.address().host())
					.port(member.address().port())
					.namespace(member.namespace())
					.build()
			).collect(Collectors.toList())
		).orElse(Collections.emptyList());
    }

	@Override
	public Mono<Void> spreadMessage(CyberClusterMessage message) {
		return Mono.when(
			cluster.otherMembers().stream().map(member ->
				Optional.ofNullable(cluster).map(cluster ->
					cluster.send(member, Message.fromData(message)).then()
				).orElse(Mono.empty())
			).collect(Collectors.toList())
		);
	}

	@Override
	public Mono<Void> spreadMessage(CyberClusterMessage message, String name) {
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
			log.info("cluster accept message {} ", message);
			messageSinks.emitNext(message.data(), new RetryNonSerializedEmitFailureHandler());
		}

		@Override
		public void onGossip(Message message) {
			log.info("cluster accept message {} ", message);
			messageSinks.emitNext(message.data(), new RetryNonSerializedEmitFailureHandler());
		}

		@Override
		public void onMembershipEvent(MembershipEvent event) {
			log.info("cluster onMembershipEvent {} ", event);
			switch (event.type()) {
				case ADDED   -> eventSinks.tryEmitNext(CyberClusterEvent.ADDED);
				case LEAVING -> eventSinks.tryEmitNext(CyberClusterEvent.LEAVING);
				case REMOVED -> eventSinks.tryEmitNext(CyberClusterEvent.REMOVED);
				case UPDATED -> eventSinks.tryEmitNext(CyberClusterEvent.UPDATED);
			}
		}
	}
}

/* 
    public boolean appendType(CyberType type) {
        return types.add(type);
    }

    public boolean appendType(CyberType... types) {
        return appendType(Arrays.stream(types).toList());
    }

    public boolean appendType(Collection<CyberType> types) {
        return types.addAll(types);
    }

    public boolean cancelType(CyberType type) {
        return types.remove(type);
    }

    public boolean cancelType(CyberType... types) {
        return cancelType(Arrays.stream(types).toList());
    }

    public boolean cancelType(Collection<CyberType> types) {
        return types.removeAll(types);
    }

    public boolean containsType(CyberType type) {
        return types.contains(type);
    }

    public boolean containsType(CyberType... types) {
        return containsType(Arrays.stream(types).toList());
    }

    public boolean containsType(Collection<CyberType> types) {
        return types.containsAll(types);
    }
*/
