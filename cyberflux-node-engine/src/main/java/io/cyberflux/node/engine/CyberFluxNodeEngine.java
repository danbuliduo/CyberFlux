package io.cyberflux.node.engine;

import io.cyberflux.meta.reactor.ClusterNode;
import io.cyberflux.meta.reactor.Reactor;
import reactor.core.publisher.Flux;

public interface CyberFluxNodeEngine extends ClusterNode {
   public Flux<Reactor> findReactor();
}
