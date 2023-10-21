package io.cyberflux.meta.cluster;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ClusterEvent implements Serializable {

    private String clusterId;
    private Type type;

    public static enum Type {
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
        UPDATED;
    }
}
