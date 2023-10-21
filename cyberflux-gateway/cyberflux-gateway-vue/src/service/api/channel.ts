import { gatewayClient } from "@/utils";
import { Method } from "@/enums/restful";
import type { ChannelState } from "@/store/modules/channel";

export function get() {
  return gatewayClient.request<ChannelState>({
    url: "/channel",
    method: Method.GET,
  });
}
