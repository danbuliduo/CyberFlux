package io.cyberflux.meta.cluster;

import java.util.Set;

import io.cyberflux.meta.data.CyberMessage;

public interface CyberFluxClusterNode {
    /**
     * @return 节点名称
     */
    String nodeName();
    /**
     * @return 获取集群中的某个成员
     */
    CyberFluxClusterNode nodeMember(String nodeName);
    /**
     * @return 获取集群中所有的成员
     */
    Set<CyberFluxClusterNode> nodeMembers();

	<M> void spreadMessage(CyberMessage message);

	<M> void spreadMessage(CyberMessage message, String modeName);

}
