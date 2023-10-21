import { gatewayClient } from '@/utils'
import { Method } from '@/enums/restful'
import type { Account } from '@/store/modules/account'
import { SFCParseResult } from 'vue/compiler-sfc';

export function get() {
  return gatewayClient.request<Account[]>({
    url: "/account",
    method: Method.GET,
  });
}

export function login(account: object) {
  return gatewayClient.request<Result>({
    url: "/account/login",
    method: Method.POST,
    data: account,
  });
}

export function logout() {
  return gatewayClient.request<Result>({
    url: "/account/logout",
    method: Method.POST,
  });
}

export function reverseauth() {
  return gatewayClient.request<Result>({
    url: "/account/reverseauth",
    method: Method.POST,
  });
}

export function post(account: object) {
  return gatewayClient.request<Result>({
    url: "/account",
    method: Method.POST,
    data: account,
  });
}

export function delByUsername(username: string) {
  return gatewayClient.request<Result>({
    url: "/account/" + username,
    method: Method.DELETE,
  });
}

