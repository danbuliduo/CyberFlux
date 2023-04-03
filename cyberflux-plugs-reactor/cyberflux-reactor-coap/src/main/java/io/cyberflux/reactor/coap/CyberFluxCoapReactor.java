package io.cyberflux.reactor.coap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.CyberFluxAbstractReactor;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import reactor.core.publisher.Mono;

public class CyberFluxCoapReactor extends CyberFluxAbstractReactor {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxCoapReactor.class);

    public CyberFluxCoapReactor() {
        super(MediumType.COAP);
    }

    @Override
    public Mono<CyberFluxReactor> start() {
        log.info("OPENING => [uuid:{} - type:{}]", uuid, type);
        return Mono.empty().thenReturn(this);
    }

    @Override
    public void shutdown() {
        log.info("CLOSING => [uuid:{} - type:{}]", uuid, type);
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
