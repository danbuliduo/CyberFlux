import store from '@/store'
import { defineStore } from 'pinia'
import { WEB_TOKEN_KEY, ServiceCode } from '@/enums'
import { storage } from '@/utils'
import { login } from '@/service/api/account.api'
import { Result } from '@/utils/src/axios'

export interface AccountState {
  webtoken: string
  username: string
  permissions: any[]
}

export const useAccountStore = defineStore({
  id: 'account',
  state: (): AccountState => ({
    webtoken: storage.get(WEB_TOKEN_KEY, ''),
    username: '',
    permissions: [],
  }),
  getters: {
    getWebToken(): string {
      return this.webtoken
    },
    getUserName(): string {
      return this.username
    },
    getPermissions(): any[] {
      return this.permissions
    }
  },
  actions: {
    setWebToken(webtoken: string) {
      this.webtoken = webtoken
    },
    setPermissions(permissions: any[]) {
      this.permissions = permissions
    },
    async login(account: any): Promise<Result> {
      try {
        const response = await login(account)
        const { payload, code } = response
        if (code === ServiceCode.SUCCEED) {
          this.setWebToken(payload.token)
          const ex = 7 * 24 * 60 * 60;

          console.log(WEB_TOKEN_KEY, payload.token)
          storage.set(WEB_TOKEN_KEY, payload.token, ex)
        }
        return Promise.resolve(response)
      } catch(e) {
        return Promise.reject(e)
      }
    }
  }
})

/**
 * @tips 需要在setup()之外使用
 */
export function useAccountStoreWidthOut() {
  return useAccountStore(store)
}
