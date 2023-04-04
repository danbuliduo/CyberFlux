package io.cyberflux.reactor.coap;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.AbstractReactor;

public class CoapReactor extends AbstractReactor {

    public CoapReactor() {
        super(MediumType.COAP);
    }

    public static CoapReactor.CoapReactorBuilder builder() {
        return new CoapReactor.CoapReactorBuilder();
    }

    public static class CoapReactorBuilder {
        public CoapReactor build() {
            return new CoapReactor();
        }
    }
}
