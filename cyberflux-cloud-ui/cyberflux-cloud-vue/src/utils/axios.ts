import axios, { AxiosResponse } from 'axios'


axios.defaults.timeout = 5000
axios.defaults.withCredentials = true

export enum Method {
  GET = 'get',
  POST = 'post',
}

async function request(url: string, mthod: Method, params: any, config: any)
  : Promise<AxiosResponse<any>> {
  switch(mthod) {
    case Method.GET:
      return axios.get(url, {params, ...config})
    case Method.POST:
      return axios.post(url, params, config)
    default: break
  }
}

export {
  request
}

