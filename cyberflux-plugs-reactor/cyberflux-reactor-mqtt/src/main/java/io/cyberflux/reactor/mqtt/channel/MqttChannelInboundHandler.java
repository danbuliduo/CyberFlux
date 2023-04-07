package io.cyberflux.reactor.mqtt.channel;


public interface MqttChannelInboundHandler extends MqttChannelMessageHandler {
	void channelActivate(MqttChannelHandlerContext context, MqttChannel channel);
    void channelRegister(MqttChannel channel);
    void channelInactive(MqttChannel channel);
}





