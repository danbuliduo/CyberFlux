package io.cyberflux.utils.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BannerUtils {

    private final static Logger log = LoggerFactory.getLogger(BannerUtils.class);

    public static void display(String path) {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(BannerUtils.class.getResourceAsStream(path), "UTF-8")
            );
            reader.lines().forEach(System.out::println);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}