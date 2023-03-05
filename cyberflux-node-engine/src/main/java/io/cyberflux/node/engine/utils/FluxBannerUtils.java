package io.cyberflux.node.engine.utils;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class FluxBannerUtils {
    public static void banner() {
        doBanner().subscribe(System.out::println);
    }
    public static Flux<String> doBanner() {
        return Flux.fromStream(
            new BufferedReader(new InputStreamReader(
                FluxBannerUtils.class.getResourceAsStream("defaultbanner.txt")
            )).lines()
        ).doOnError(Throwable::printStackTrace);
    }
}