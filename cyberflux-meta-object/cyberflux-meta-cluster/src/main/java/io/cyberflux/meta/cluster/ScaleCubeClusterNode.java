package io.cyberflux.meta.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.Member;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import io.scalecube.reactor.RetryNonSerializedEmitFailureHandler;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


public class ScaleCubeClusterNode extends CyberObject implements CyberClusterNode {
	private static final Logger log = LoggerFactory.getLogger(ScaleCubeClusterNode.class);
    protected final EnumSet<CyberType> types;
    protected final String nodeName;
	protected Sinks.Many<CyberClusterMessage> messageSinks;
	protected Sinks.Many<CyberClusterEvent> eventSinks;
	protected Cluster thatCluster;

    public ScaleCubeClusterNode(String nodeName) {
        this(nodeName, EnumSet.allOf(CyberType.class));
    }

    public ScaleCubeClusterNode(String nodeName, CyberType... types) {
        this(nodeName, Arrays.stream(types).toList());
    }

    public ScaleCubeClusterNode(String nodeName, Collection<CyberType> types) {
        this(nodeName, EnumSet.copyOf(types));
    }

    public ScaleCubeClusterNode(String nodeName, EnumSet<CyberType> types) {
		super(CyberType.GOSSIP);
        this.types = EnumSet.copyOf(types);
        this.nodeName = nodeName;
    }

    @Override
    public String name() {
        return this.nodeName;
    }

    @Override
    public CyberClusterMember member(String nodeName) {
        return null;
    }

    @Override
    public List<CyberClusterMember> members() {
        return Optional.ofNullable(thatCluster).map(member ->
			thatCluster.members().stream().map(cluster->
				CyberClusterMember.builder()
					.id(cluster.id())
					.alias(cluster.alias())
					.host(cluster.address().host())
					.port(cluster.address().port())
					.namespace(cluster.namespace())
					.build()
			).collect(Collectors.toList())
		).orElse(Collections.emptyList());
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
	@Override
	public Mono<Void> spreadMessage(CyberClusterMessage message) {
		log.info("Cluster send Message{}", message);
		return Mono.when(
			thatCluster.otherMembers().stream().map(member -> {
				return Optional.ofNullable(thatCluster).map(cluster ->
					cluster.send(member, Message.fromData(message)).then()
				).orElse(Mono.empty());
			}).collect(Collectors.toList())
		);
	}

	@Override
	public Mono<Void> spreadMessage(CyberClusterMessage message, String modeName) {
		throw new UnsupportedOperationException("Unimplemented method 'spreadMessage'");
	}

	@Override
	public Mono<Void> shutdown() {
		return Mono.fromRunnable(() ->
			Optional.ofNullable(thatCluster).ifPresent(Cluster::shutdown)
		);
	}

	private class ClusterHandler implements ClusterMessageHandler {
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
			Member member = event.member();
			log.info("cluster onMembershipEvent {}  {}", member, event);
			switch (event.type()) {
				case ADDED   -> eventSinks.tryEmitNext(CyberClusterEvent.ADDED);
				case LEAVING -> eventSinks.tryEmitNext(CyberClusterEvent.LEAVING);
				case REMOVED -> eventSinks.tryEmitNext(CyberClusterEvent.REMOVED);
				case UPDATED -> eventSinks.tryEmitNext(CyberClusterEvent.UPDATED);
			}
		}
	}
}
