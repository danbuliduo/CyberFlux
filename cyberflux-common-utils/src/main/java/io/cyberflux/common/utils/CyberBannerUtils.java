package io.cyberflux.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CyberBannerUtils {
    private final static Logger log = LoggerFactory.getLogger(CyberBannerUtils.class);

    public static void displayBanner(String path) {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(CyberBannerUtils.class.getResourceAsStream(path))
            );
            reader.lines().forEach(System.out::println);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}