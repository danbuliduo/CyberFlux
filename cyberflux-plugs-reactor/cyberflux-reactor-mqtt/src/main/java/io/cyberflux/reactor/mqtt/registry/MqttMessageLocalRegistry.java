package io.cyberflux.reactor.mqtt.registry;

import java.util.List;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;

public final class MqttMessageLocalRegistry implements MqttMessageRegistry {
    private final Cache<String, MqttRetainMessage> retainCache;
    private final Cache<String, List<MqttSessionMessage>> sessionCache;

    public MqttMessageLocalRegistry() {
        retainCache = Caffeine.newBuilder().build();
        sessionCache = Caffeine.newBuilder().build();
    }

    @Override
    public List<MqttSessionMessage> findSessionMessage(String clientId) {
        return null;
    }

    @Override
    public List<MqttRetainMessage> findRetainMessage(String topic) {
        return null;
    }

    @Override
    public void saveSessionMessage(MqttSessionMessage sessionMessage) {
        List<MqttSessionMessage> dd = sessionCache.get(sessionMessage.getClientId(), null);
    }

    @Override
    public void saveRetainMessage(MqttRetainMessage retainMessage) {
        
    }
}
