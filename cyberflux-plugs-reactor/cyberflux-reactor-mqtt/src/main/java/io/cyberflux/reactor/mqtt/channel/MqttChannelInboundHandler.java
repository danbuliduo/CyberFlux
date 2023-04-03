package io.cyberflux.reactor.mqtt.channel;


public interface MqttChannelInboundHandler extends MqttChannelMessageHandler {
    void channelRegister(MqttChannel channel);
    void channelInactive(MqttChannel channel);
}





