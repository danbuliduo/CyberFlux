package io.cyberflux.meta.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;
//import io.scalecube.cluster.ClusterImpl;
//import io.scalecube.cluster.transport.api.Message;

public abstract class CyberFluxAbstractClusterNode extends CyberObject implements CyberFluxClusterNode {
    protected final Map<String, CyberFluxClusterNode> nodeMembers;
    protected final EnumSet<CyberType> types;
    protected final String nodeName;

    public CyberFluxAbstractClusterNode(String nodeName) {
        this(nodeName, EnumSet.allOf(CyberType.class));
    }

    public CyberFluxAbstractClusterNode(String nodeName, CyberType... types) {
        this(nodeName, Arrays.stream(types).toList());
    }

    public CyberFluxAbstractClusterNode(String nodeName, Collection<CyberType> types) {
        this(nodeName, EnumSet.copyOf(types));
    }

    public CyberFluxAbstractClusterNode(String nodeName, EnumSet<CyberType> types) {
		super(CyberType.GOSSIP);
        this.nodeMembers = new ConcurrentHashMap<>();
        this.types = EnumSet.copyOf(types);
        this.nodeName = nodeName;
    }

    @Override
    public final String nodeName() {
        return this.nodeName;
    }

    @Override
    public final CyberFluxClusterNode nodeMember(String nodeName) {
        return nodeMembers.get(nodeName);
    }

    @Override
    public Set<CyberFluxClusterNode> nodeMembers() {
        return new HashSet<>(nodeMembers.values());
    }

    public boolean appendMediumType(CyberType type) {
        return types.add(type);
    }

    public boolean appendType(CyberType... types) {
        return appendType(Arrays.stream(types).toList());
    }

    public boolean appendType(Collection<CyberType> types) {
        return types.addAll(types);
    }

    public boolean cancelType(CyberType type) {
        return types.remove(type);
    }

    public boolean cancelType(CyberType... types) {
        return cancelType(Arrays.stream(types).toList());
    }

    public boolean cancelType(Collection<CyberType> types) {
        return types.removeAll(types);
    }

    public boolean containsType(CyberType type) {
        return types.contains(type);
    }

    public boolean containsType(CyberType... types) {
        return containsType(Arrays.stream(types).toList());
    }

    public boolean containsType(Collection<CyberType> types) {
        return types.containsAll(types);
    }
}
