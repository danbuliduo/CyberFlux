package io.cyberflux.meta.channel;

import io.cyberflux.meta.lang.MetaAttribute;
import reactor.core.publisher.Mono;

public interface Channel extends MetaAttribute {
    Mono<Void> close();
}
