package io.cyberflux.meta.reactor;

import java.util.Set;

public interface ClusterNode {
    /**
     * @return 节点名称
     */
    public String nodeName();
    /**
     * @return 获取集群中的某个成员
     */
    public ClusterNode nodeMember(String nodeName);
    /**
     * @return 获取集群中所有的成员
     */
    public Set<ClusterNode> nodeMembers();
}
