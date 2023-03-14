package io.cyberflux.meta.reactor;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractClusterNode implements ClusterNode {
    protected EnumSet<ProtocolType> supportProtocols;
    protected Map<String, ClusterNode> nodeMembers;
    protected String nodeName;

    public AbstractClusterNode(String nodeName) {
        this.nodeName = nodeName;
        nodeMembers = new ConcurrentHashMap<>();
    }

    @Override
    public String nodeName() {
        return this.nodeName;
    }

    @Override
    public ClusterNode nodeMember(String nodeName) {
        return nodeMembers.get(nodeName);
    }

    @Override
    public Set<ClusterNode> nodeMembers() {
        return new HashSet<>(nodeMembers.values());
    }

    public void addProtocolType(ProtocolType type) {

    }
}
