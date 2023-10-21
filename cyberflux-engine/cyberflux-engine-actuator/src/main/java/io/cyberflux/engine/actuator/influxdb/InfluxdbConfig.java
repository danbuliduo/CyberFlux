package io.cyberflux.engine.actuator.influxdb;

import java.time.Duration;

import io.micrometer.influx.InfluxConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfluxdbConfig {

    private boolean enable = false;
    private String db = "";
    private String uri = "http://localhost:8086";
    private String org = "";
    private String token = "";
    private String bucket = "";
    private String username = "";
    private String password = "";
    private Duration step = Duration.ofSeconds(10);

    public static InfluxConfig convert(InfluxdbConfig that) {
        return new InfluxConfig() {

            @Override
            public Duration step() {
                if(that.step == null) {
                    return Duration.ofSeconds(10);
                }
                return Duration.ofSeconds(Math.max(that.step.getSeconds(), 10));
            }

            @Override
            public String db() {
                return that.db;
            }

            @Override
            public String uri() {
                return that.uri;
            }

            @Override
            public String org() {
                return that.org;
            }

            @Override
            public String bucket() {
                return that.bucket;
            }

            @Override
            public String token() {
                return that.token;
            }

            @Override
            public String userName() {
                return that.username;
            }

            @Override
            public String password() {
                return that.password;
            }

            @Override
            public String get(String key) {
                return null;
            }
        };
    }
}
