package io.cyberflux.reactor.coap;

import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import reactor.core.publisher.Mono;

public class CyberFluxCoapReactor extends AbstractReactor {

    public CyberFluxCoapReactor() {
        super(ReactorType.COAP);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public Mono<Reactor> start() {
       return Mono.empty().thenReturn(this);
    }

    public static CyberFluxCoapReactor.CoapReactorBuilder builder() {
        return new CyberFluxCoapReactor.CoapReactorBuilder();
    }

    public static class CoapReactorBuilder {
        public CyberFluxCoapReactor build() {
            return new CyberFluxCoapReactor();
        }
    }
}
