package io.cyberflux.cloud.core.service;

import java.util.Map;
import java.util.Set;

import io.cyberflux.meta.models.node.NodeEngineModel;

public interface NodeEngineService {
	void register(NodeEngineModel model);
	Map<String, Set<NodeEngineModel>> findAll();
}
