package io.cyberflux.reactor.mqtt.registry;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;
import io.cyberflux.reactor.mqtt.utils.MqttTopicFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicRegexUtils;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTableFilter;
import io.cyberflux.reactor.mqtt.utils.MqttTopicTreeFilter;

public final class DefaultTopicRegistry implements MqttSubTopicRegistry {

    private MqttTopicFilter tableFilter;

    private MqttTopicFilter treeFilter;

    public DefaultTopicRegistry() {
        tableFilter = new MqttTopicTableFilter();
        treeFilter = new MqttTopicTreeFilter();
    }

	@Override
    public Set<MqttSubTopicStore> findAll() {
		return Stream.concat(
			treeFilter.getAllTopicStore().stream(),
			treeFilter.getAllTopicStore().stream()
		).collect(Collectors.toSet());
    }

    @Override
    public Set<MqttSubTopicStore> findByTopic(String topic) {
		if(MqttTopicRegexUtils.containWildcard(topic)) {
			return treeFilter.getTopicStore(topic);
		}
		return tableFilter.getTopicStore(topic);
    }

	@Override
	public boolean append(MqttSubTopicStore store) {
		if(store.containWildcard()) {
			return treeFilter.appendTopicStore(store);
		}
		return tableFilter.appendTopicStore(store);
	}

	@Override
	public boolean remove(MqttSubTopicStore store) {
		if(store.containWildcard()) {
			return treeFilter.removeTopicStore(store);
		}
		return tableFilter.removeTopicStore(store);
	}

	@Override
	public void appendAll(Set<MqttSubTopicStore> stores) {
		stores.forEach(this::append);
	}

	@Override
	public void removeAll(Set<MqttSubTopicStore> stores) {
		stores.forEach(this::remove);
	}

    @Override
    public long count() {
        return treeFilter.count() + tableFilter.count();
    }

}
