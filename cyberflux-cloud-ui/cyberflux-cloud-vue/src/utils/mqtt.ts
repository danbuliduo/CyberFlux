import mqtt, { IClientOptions, MqttClient } from 'mqtt'


function createMqttClient (optios: IClientOptions) : MqttClient {
  return mqtt.connect(optios)
}

export {
  createMqttClient
}