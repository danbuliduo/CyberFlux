package io.cyberflux.reactor.coap;

import io.cyberflux.common.utils.CyberNanoIdUtils;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberClusterHandler;
import io.cyberflux.meta.reactor.TemplateReactor;

public class CoapReactor extends TemplateReactor {

    public CoapReactor() {
        super(CyberType.COAP, CyberNanoIdUtils.randomNanoId());
    }

    public static CoapReactor.CoapReactorBuilder builder() {
        return new CoapReactor.CoapReactorBuilder();
    }

    public static class CoapReactorBuilder {
        public CoapReactor build() {
            return new CoapReactor();
        }
    }

	@Override
	public CyberClusterHandler clusterHandler() {
		return null;
	}
}
