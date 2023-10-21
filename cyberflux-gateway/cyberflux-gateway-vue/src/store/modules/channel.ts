import { defineStore } from "pinia";
import { get } from "@/service/api/channel";



export type ChannelState = Tuple2<string, Tuple2<string, Channel[]>[]>[];

export interface Channel {
  address: string;
  clientId: string;
  username: string;
  online: string;
  keepAlive: number;
  connectTime: number;
  disconnectTime: number;
}

export interface MqttChannel extends Channel {
  cleanSession: boolean;
  sessionExpiry: number;
  version: number;
}

export const useChannelStore = defineStore('channel', () => {
  var state: ChannelState = [];

  async function update(): Promise<ChannelState> {
    try {
      const response = await get();
      state = response;
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  };

  function collectByType<T extends Channel>(type: string): T[] {
    let data: any[] = [];
    state.forEach((value1) => {
      value1.t2.forEach((value2) => {
        if (value2.t1 == type) {
          data = [...data, ...value2.t2];
        }
      });
    });
    return data;
  }

  return {state, update, collectByType };
});


