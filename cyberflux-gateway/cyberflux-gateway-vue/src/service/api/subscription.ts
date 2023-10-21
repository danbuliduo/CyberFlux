import { gatewayClient } from "@/utils";
import { Method } from "@/enums/restful";
import { Subscription } from "@/store/modules/subscription";

export function get() {
  return gatewayClient.request<Tuple2<string, Subscription[]>[]>({
    url: "/subscription",
    method: Method.GET,
  });
}

export function getCount() {
  return gatewayClient.request<Tuple2<string, Subscription[]>[]>({
    url: "/subscription/count",
    method: Method.GET,
  });
}
