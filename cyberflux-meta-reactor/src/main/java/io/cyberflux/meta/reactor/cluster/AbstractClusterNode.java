package io.cyberflux.meta.reactor.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.reactor.ReactorType;

public abstract class AbstractClusterNode implements ClusterNode {
    protected final Map<String, ClusterNode> nodeMembers;
    protected final EnumSet<ReactorType> supportTypes;
    protected final String nodeName;

    public AbstractClusterNode(String nodeName) {
        this(nodeName, EnumSet.allOf(ReactorType.class));
    }

    public AbstractClusterNode(String nodeName, ReactorType... types) {
        this(nodeName, Arrays.stream(types).toList());
    }

    public AbstractClusterNode(String nodeName, Collection<ReactorType> types) {
        this(nodeName, EnumSet.copyOf(types));
    }

    public AbstractClusterNode(String nodeName, EnumSet<ReactorType> types) {
        this.nodeMembers = new ConcurrentHashMap<>();
        this.supportTypes = EnumSet.copyOf(types);
        this.nodeName = nodeName;
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

    public boolean appendSupportType(ReactorType type) {
        return supportTypes.add(type);
    }

    public boolean appendSupportType(ReactorType... types) {
        return appendSupportType(Arrays.stream(types).toList());
    }

    public boolean appendSupportType(Collection<ReactorType> types) {
        return supportTypes.addAll(types);
    }

    public boolean removeSupportType(ReactorType type) {
        return supportTypes.remove(type);
    }

    public boolean removeSupportType(ReactorType... types) {
        return removeSupportType(Arrays.stream(types).toList());
    }

    public boolean removeSupportType(Collection<ReactorType> types) {
        return supportTypes.removeAll(types);
    }

    public boolean containsSupportType(ReactorType type) {
        return supportTypes.contains(type);
    }

    public boolean containsSupportType(ReactorType... types) {
        return containsSupportType(Arrays.stream(types).toList());
    }

    public boolean containsSupportType(Collection<ReactorType> types) {
        return supportTypes.containsAll(types);
    }
}
