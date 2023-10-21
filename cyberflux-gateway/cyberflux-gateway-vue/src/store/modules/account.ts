import { defineStore } from 'pinia';
import { ResponseCode } from '@/enums/restful';
import { WEB_TOKEN_KEY } from '@/constant'
import { storage } from "@/utils";
import * as AccountApi from '@/service/api/account';


export type AccountState = Map<string, Account>

export interface Account {
  username: string;
  password: string;
  remarks: string;
  permissions: string[];
}

export interface User extends Account {
  webtoken: string;
}

export const useAccountStore = defineStore('account', () => {

  const state: AccountState = new Map<string, Account>();

  let user: User = reactive({
    webtoken: '',
    username: '',
    password: '',
    remarks: '',
    permissions: [],
  });

  function porfile() {
    return {
      username: user.username,
      password: user.password,
      remarks: user.remarks,
      permissions: user.permissions,
    };
  }

  async function update(): Promise<AccountState> {
    try {
      const response = await AccountApi.get();
      response.forEach(item => state.set(item.username, item))
      return Promise.resolve(state);
    } catch (e) {
      return Promise.reject(e);
    }
  }

  async function login(account: any): Promise<Result> {
    try {
      const response = await AccountApi.login(account);
      const { code, header, payload } = response;
      if (code === ResponseCode.ACCE) {
        user.username = payload.username;
        user.remarks = payload.remarks;
        user.permissions = payload.permissions;
        user.webtoken = header.token;
        storage.set(WEB_TOKEN_KEY, header.token, header.expires);
      }
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  }
  async function logout(): Promise<Result> {
    try {
      const response = await AccountApi.logout();
      const { code } = response;
      if (code === ResponseCode.ACCE) {
        storage.remove(WEB_TOKEN_KEY);
        user = {
          webtoken: "",
          username: "",
          password: "",
          remarks: "",
          permissions: [],
        };
      }
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  }
  async function reverseauth(): Promise<Result> {
    try {
      user.webtoken = storage.get(WEB_TOKEN_KEY)
      if(!user.webtoken) {
        window.$message.info("认证已失效，请重新登陆")
        return Promise.resolve({
          code: ResponseCode.REFU
        });
      }
      const response = await AccountApi.reverseauth();
      const { payload } = response;
      user.username = payload.username;
      user.remarks = payload.remarks;
      user.permissions = payload.permissions;
      return Promise.resolve(response);
    } catch (e) {
      return Promise.reject(e);
    }
  }
  return {
    state,
    user,
    update,
    reverseauth,
    porfile,
    login,
    logout,
  };
});
