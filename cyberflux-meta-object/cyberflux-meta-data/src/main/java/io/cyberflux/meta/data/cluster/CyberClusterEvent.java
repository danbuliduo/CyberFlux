package io.cyberflux.meta.data.cluster;

public enum CyberClusterEvent {
	/**
     * 添加
     */
    ADDED,
    /**
     * 移除
     */
    REMOVED,

    /**
     * 离开
     */
    LEAVING,
    /**
     * 更新
     */
    UPDATED
}
