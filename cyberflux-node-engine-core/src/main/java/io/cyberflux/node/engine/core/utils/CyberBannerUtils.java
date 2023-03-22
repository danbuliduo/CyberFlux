package io.cyberflux.node.engine.core.utils;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class CyberBannerUtils {
    public static void banner() {
        CyberBannerUtils.doBanner("/io.cyberflux.banner.txt").subscribe(System.out::println);
    }
    public static void banner(String path) {
        CyberBannerUtils.doBanner(path).subscribe(System.out::println);
    }
    public static Flux<String> doBanner(String path) {
        return Flux.fromStream(new BufferedReader(
            new InputStreamReader(CyberBannerUtils.class.getResourceAsStream(path))).lines()
        ).doOnError(Throwable::printStackTrace);
    }
}