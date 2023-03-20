package io.cyberflux.reactor.coap;

import io.cyberflux.meta.reactor.CyberFluxAbstractReactor;
import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import reactor.core.publisher.Mono;

public class CyberFluxCoapReactor extends CyberFluxAbstractReactor {

    public CyberFluxCoapReactor() {
        super(ReactorType.COAP);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public Mono<CyberFluxReactor> start() {
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
