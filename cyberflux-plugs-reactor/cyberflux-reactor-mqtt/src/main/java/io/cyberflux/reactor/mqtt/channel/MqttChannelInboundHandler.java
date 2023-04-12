package io.cyberflux.reactor.mqtt.channel;


public interface MqttChannelInboundHandler extends MqttChannelMessageHandler {
	void channelActivate(MqttChannelContext context, MqttChannel channel);
    void channelRegister(MqttChannelContext context, MqttChannel channel);
    void channelInactive(MqttChannelContext context, MqttChannel channel);
}





