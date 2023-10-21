import { defineStore } from "pinia";
import { NodeStatus } from "@/enums/cluster";
import * as EngineApi from "@/service/api/engine";


export type EngineState = Map<string, Engine>

export interface Engine {
  id: string;
  host: string;
  name: string;
  mode: string;
  version: string;
  start: number;
  status: NodeStatus;
}

export const useEngineStore = defineStore('engine', () => {
  let state: EngineState = new Map<string, Engine>();
  async function update(): Promise<Engine[]> {
    try {
      const response = await EngineApi.get();
      response.forEach((item) => state.set(item.host, item));
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  };
  return { state, update }
});
