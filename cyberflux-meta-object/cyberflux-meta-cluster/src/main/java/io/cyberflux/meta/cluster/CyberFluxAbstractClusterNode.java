package io.cyberflux.meta.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.medium.MediumType;


public abstract class CyberFluxAbstractClusterNode implements CyberFluxClusterNode {
    protected final Map<String, CyberFluxClusterNode> nodeMembers;
    protected final EnumSet<MediumType> mediumTypes;
    protected final String nodeName;

    public CyberFluxAbstractClusterNode(String nodeName) {
        this(nodeName, EnumSet.allOf(MediumType.class));
    }

    public CyberFluxAbstractClusterNode(String nodeName, MediumType... types) {
        this(nodeName, Arrays.stream(types).toList());
    }

    public CyberFluxAbstractClusterNode(String nodeName, Collection<MediumType> types) {
        this(nodeName, EnumSet.copyOf(types));
    }

    public CyberFluxAbstractClusterNode(String nodeName, EnumSet<MediumType> types) {
        this.nodeMembers = new ConcurrentHashMap<>();
        this.mediumTypes = EnumSet.copyOf(types);
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

    public boolean appendMediumType(MediumType type) {
        return mediumTypes.add(type);
    }

    public boolean appendMediumType(MediumType... types) {
        return appendMediumType(Arrays.stream(types).toList());
    }

    public boolean appendMediumType(Collection<MediumType> types) {
        return mediumTypes.addAll(types);
    }

    public boolean removeMediumType(MediumType type) {
        return mediumTypes.remove(type);
    }

    public boolean removeMediumType(MediumType... types) {
        return removeMediumType(Arrays.stream(types).toList());
    }

    public boolean removeMediumType(Collection<MediumType> types) {
        return mediumTypes.removeAll(types);
    }

    public boolean containsMediumType(MediumType type) {
        return mediumTypes.contains(type);
    }

    public boolean containsMediumType(MediumType... types) {
        return containsMediumType(Arrays.stream(types).toList());
    }

    public boolean containsMediumType(Collection<MediumType> types) {
        return mediumTypes.containsAll(types);
    }
}
