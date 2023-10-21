package io.cyberflux.gateway.app.context;

import io.cyberflux.cluster.ClusterMemberEventListener;
import io.cyberflux.cluster.ClusterOption;
import io.cyberflux.engine.spring.SpringEngine;
import io.cyberflux.gateway.app.codec.EngineEntity;
import io.cyberflux.gateway.app.repository.EngineMemRepository;
import io.cyberflux.utils.net.HostUtils;
import io.scalecube.cluster.Member;
import io.scalecube.cluster.membership.MembershipEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class GatewayIInitializingBean implements InitializingBean {

    private final SpringEngine springEngine;
    private final EngineMemRepository engineMemRepository;

    @Value("${server.port}")
    private int port;
    @Override
    public void afterPropertiesSet() throws Exception {
        springEngine.context().config().setApiPort(port);
        ClusterOption clusterOption = springEngine.context().config().getCluster();
        Flux.fromArray(clusterOption.getNodes())
            .map(item -> {
                String[] addr = item.split(":");
                if (HostUtils.isLocalIp(addr[0])) {
                    addr[0] = HostUtils.getLocalIp();
                }
                return EngineEntity.fromOnlyHost(addr[0] + ":" + addr[1]);
            }).subscribe(engineMemRepository::save);
        EngineEntity localEngine = new EngineEntity();
        localEngine.setId(springEngine.id());
        localEngine.setHost(springEngine.host());
        localEngine.setName(springEngine.name());
        localEngine.setMode(springEngine.mode());
        localEngine.setApiPath(springEngine.apiPath());
        localEngine.setVersion(springEngine.version());
        localEngine.setStatus(EngineEntity.Status.UP);
        localEngine.setStart(System.currentTimeMillis());
        engineMemRepository.save(localEngine);
        initEngineListener();
    }

    private void  initEngineListener() {
        GatewaySendEventContext sendEventContext = GatewaySendEventContext.instance();
        springEngine.addEventListener(event -> {
            Member member = event.member();
            switch (event.type()) {
                case ADDED -> {
                    EngineEntity entity = EngineEntity.fromClusterMember(member);
                    entity.setStart(System.currentTimeMillis());
                    entity.setStatus(EngineEntity.Status.RESTRICTED);
                    engineMemRepository.save(entity);
                }
                case LEAVING -> {
                    engineMemRepository.findByHost(member.address().toString())
                            .subscribe(item -> item.setStatus(EngineEntity.Status.OUT_OF_SERVICE));
                }
                case REMOVED -> {
                    engineMemRepository.findByHost(member.address().toString())
                            .subscribe(item -> item.setStatus(EngineEntity.Status.DOWN));
                }
                case UPDATED -> {
                    engineMemRepository.findByHost(member.address().toString())
                            .subscribe(item -> {
                                item.setStatus(EngineEntity.Status.RESTRICTED);
                            });
                }
            }
        });
        springEngine.addEventListener(event -> {
            Member member = event.member();
            String type = switch (event.type()) {
                case ADDED   -> "engine-added";
                case LEAVING -> "engine-leaving";
                case REMOVED -> "engine-remove";
                case UPDATED -> "engine-update";
            };
            engineMemRepository.findByHost(member.address().toString()).subscribe(item -> {
                sendEventContext.emit(ServerSentEvent.builder(item).event(type).build());
            });
        });
    }
}
