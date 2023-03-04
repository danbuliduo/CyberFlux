package io.cyberflux.flux.mqtt;

import io.cyberflux.meta.reactor.core.ReactiveServer;
import io.cyberflux.reactor.mqtt.MqttServer;
import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.transport.api.TransportFactory;

import io.scalecube.transport.netty.websocket.WebsocketTransportFactory;
import io.scalecube.transport.netty.tcp.TcpTransportFactory;
import reactor.netty.tcp.TcpServer;
import io.scalecube.cluster.ClusterMessageHandler;

@SuppressWarnings("all")
public class ClusterTest {
    public static void main(String[] args) {
        Cluster c = new ClusterImpl().transportFactory(null);
        
        ReactiveServer server = MqttServer.builder()
                .build()
                .startAwait();
    }
}
