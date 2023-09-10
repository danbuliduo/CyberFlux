package io.cyberflux.cloud.core.cache;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

import io.cyberflux.meta.models.node.NodeEngineModel;

@Component
public final class NodeEngineCache {

	private final Map<String, NodeEngineModel> models;

	public NodeEngineCache() {
		models = new ConcurrentHashMap<>(1);
	}

	public void save(NodeEngineModel model) {
		models.put(model.getId(), model);
	}

	public void remove(NodeEngineModel model) {
		models.remove(model.getId());
	}

	public Set<NodeEngineModel> findAll() {
		return new HashSet<>(models.values());
	}
}
