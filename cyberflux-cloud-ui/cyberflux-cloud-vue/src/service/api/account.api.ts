import type { Result } from '@/utils/src/axios'
import { api } from '@/utils'
import { Method } from '@/enums'

export function login(account: any) {
  return api.request<Result>({
    url: '/account/login',
    method: Method.POST,
    data: account
  }, {
    responseModel: 'data'
  })
}

export function queryAll(token: any) {
  return api.request<Result>({
    url: '/account/query-all',
    method: Method.POST,
    data: token
  }, {
    responseModel: 'data'
  })
}
