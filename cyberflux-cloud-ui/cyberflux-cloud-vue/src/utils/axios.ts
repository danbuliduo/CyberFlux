import axios, { AxiosResponse } from 'axios'
import { Method } from '@/enums/http.enum'

axios.defaults.timeout = 5000
axios.defaults.withCredentials = true

/**
 * HTTP异步请求
 * @param url URL
 * @param mthod Method.(GET | POST)
 * @param params Any
 * @param config Any
 * @returns {Promise<AxiosResponse<any>>}
 */
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

