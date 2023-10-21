package io.cyberflux.cluster;

import io.scalecube.cluster.membership.MembershipEvent;

public interface ClusterMemberEventListener {
    void handle(MembershipEvent event);
}
