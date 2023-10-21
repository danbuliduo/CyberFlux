package io.cyberflux.reactor.mqtt.registry;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;
import io.cyberflux.reactor.mqtt.utils.MqttTopicFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicRegexUtils;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTableFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTreeFilter;

public final class DefaultSubscriptionRegistry implements MqttSubscriptionRegistry {

    private MqttTopicFilter tableFilter;
    private MqttTopicFilter treeFilter;

    public DefaultSubscriptionRegistry() {
        tableFilter = new MqttTopicTableFilter();
        treeFilter = new MqttTopicTreeFilter();
    }

	@Override
    public Set<MqttSubscriptionStore> findAll() {
		return Stream.concat(
			treeFilter.getAllTopicStore().stream(),
			tableFilter.getAllTopicStore().stream()
		).collect(Collectors.toSet());
    }

    @Override
    public Set<MqttSubscriptionStore> findByTopic(String topic) {
		if(MqttTopicRegexUtils.containWildcard(topic)) {
			return treeFilter.getTopicStore(topic);
		}
		return tableFilter.getTopicStore(topic);
    }

	@Override
	public boolean append(MqttSubscriptionStore store) {
		if(store.containWildcard()) {
			return treeFilter.appendTopicStore(store);
		}
		return tableFilter.appendTopicStore(store);
	}

	@Override
	public boolean remove(MqttSubscriptionStore store) {
		if(store.containWildcard()) {
			return treeFilter.removeTopicStore(store);
		}
		return tableFilter.removeTopicStore(store);
	}

	@Override
	public void appendAll(Collection<MqttSubscriptionStore> collection) {
		collection.forEach(this::append);
	}

	@Override
	public void removeAll(Collection<MqttSubscriptionStore> collection) {
		collection.forEach(this::remove);
	}

    @Override
    public long count() {
        return treeFilter.count() + tableFilter.count();
    }

}
