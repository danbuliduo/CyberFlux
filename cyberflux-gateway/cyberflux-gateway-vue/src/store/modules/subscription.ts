import { defineStore } from "pinia";
import { get } from "@/service/api/subscription";

export type SubscriptionState = Tuple2<string, Subscription[]>[];

export interface Subscription {
  clientId: string;
  topic: string;
  level: string;
}

export interface TopicSource {
  source: string;
  topic: string;
}

export const useSubscriptionStore = defineStore("subscription", () => {
  var state: SubscriptionState = [];
  async function update(): Promise<SubscriptionState> {
    try {
      const response = await get();
      state = response;
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  };
  function clear() {
    state = []
  };
  function toArray() : Subscription[] {
    let data: Subscription[] = [];
      state.forEach((value) => {
        data = [...data, ...value.t2];
      });
      return data;
  };

  return {clear, update, toArray}
});
