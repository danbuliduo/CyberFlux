package io.cyberflux.reactor.coap;


public class CoapReactor  {

    public CoapReactor() {
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
