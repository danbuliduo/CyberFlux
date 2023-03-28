package io.cyberflux.reactor.mqtt.registry;

import java.util.Set;
import java.util.stream.Collectors;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;
import io.cyberflux.reactor.mqtt.utils.MqttTopicFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTableFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTreeFilter;
import reactor.core.publisher.Flux;

public final class MqttTopicLocalRegistry implements MqttTopicRegistry {
    private final MqttTopicFilter tableFilter;
    private final MqttTopicFilter treeFilter;

    public MqttTopicLocalRegistry() {
        tableFilter = new MqttTopicTableFilter();
        treeFilter = new MqttTopicTreeFilter();
    }

    @Override
    public boolean appendTopicStore(MqttTopicStore store) {
        return store.containWildcard() ? treeFilter.appendTopicStore(store)
                : tableFilter.appendTopicStore(store);
    }

    @Override
    public boolean removeTopicStore(MqttTopicStore store) {
        return store.containWildcard() ? treeFilter.removeTopicStore(store)
                : tableFilter.removeTopicStore(store);
    }

    @Override
    public Set<MqttTopicStore> getTopicStore(String topic) {
        return Flux.merge(
            Flux.fromIterable(treeFilter.getTopicStore(topic)),
            Flux.fromIterable(tableFilter.getTopicStore(topic))
        ).toStream().collect(Collectors.toSet());
    }

    @Override
    public Set<MqttTopicStore> getAllTopicStore() {
        return Flux.merge(
            Flux.fromIterable(treeFilter.getAllTopicStore()),
            Flux.fromIterable(tableFilter.getAllTopicStore())
        ).toStream().collect(Collectors.toSet());
    }

    @Override
    public long count() {
        return treeFilter.count() + tableFilter.count();
    }
}
