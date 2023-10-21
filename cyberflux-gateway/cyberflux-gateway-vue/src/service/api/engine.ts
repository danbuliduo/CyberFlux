import { gatewayClient } from "@/utils";
import { Method } from "@/enums/restful";
import { Engine } from "@/store/modules/engine";

export function get() {
  return gatewayClient.request<Engine[]>({
    url: "/engine",
    method: Method.GET,
  });
}
