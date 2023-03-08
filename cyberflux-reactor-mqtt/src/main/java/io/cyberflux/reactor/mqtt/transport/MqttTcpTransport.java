package io.cyberflux.reactor.mqtt.transport;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class MqttTcpTransport extends AbstractMqttTransport {

    @Override
    public Mono<DisposableServer> careatDisposableServer() {
        return TcpServer.create().port(1883).doOnConnection(coon -> {
        }).bind().cast(DisposableServer.class);
    }
}
