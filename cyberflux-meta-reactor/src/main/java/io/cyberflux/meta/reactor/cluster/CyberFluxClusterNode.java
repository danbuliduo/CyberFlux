package io.cyberflux.meta.reactor.cluster;

import java.util.Set;

public interface CyberFluxClusterNode {
    /**
     * @return 节点名称
     */
    public String nodeName();
    /**
     * @return 获取集群中的某个成员
     */
    public CyberFluxClusterNode nodeMember(String nodeName);
    /**
     * @return 获取集群中所有的成员
     */
    public Set<CyberFluxClusterNode> nodeMembers();
}
