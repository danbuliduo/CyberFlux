import mqtt, { IClientOptions, MqttClient } from 'mqtt'

/**
 * 创建MQTTClient
 * @param optios IClientOptions
 * @returns {MqttClient}
 */
function createMqttClient (optios: IClientOptions) : MqttClient {
  return mqtt.connect(optios)
}

export {
  createMqttClient
}