import type { AxiosRequestConfig, AxiosResponse, AxiosError, InternalAxiosRequestConfig } from 'axios'
import type { RequestOptions, AxiosRequestOptions } from "./axios";
import axios  from 'axios'
import { AxiosAgent, AxiosTransport, deepMerge, formatRequestDate, joinRequestTimestamp} from './axios'
import { isUrl, montageObjConvParams } from './url.util'
import { isString } from './determine.util'
import { Method, StatusCode, ResponseCode, ContentType } from '@/enums/restful';
import { AccountStore } from '@/store';

class DefaultTransport extends AxiosTransport
{
  ///////////////////////////////////////////////////////////
  transfromRequest = (config: AxiosRequestConfig, options: RequestOptions) => {
    const { apiurl, prefix, joinPrefix, joinTimestamp, joinParams, formatDate } = options
    const isurl: boolean = isUrl(config.url as string)
    if (!isurl && joinPrefix) {
      config.url = `${prefix}${config.url}`
    }
    if (!isurl && apiurl && isString(apiurl)) {
      config.url = `${apiurl}${config.url}`
    }
    const params = config.params || {}
    const data = config.data || false
    if (config.method?.toUpperCase() === Method.GET) {
      if (!isString(params)) {
        // 给 get 请求加上时间戳参数，避免从缓存中拿数据。
        config.params = Object.assign(params || {}, joinRequestTimestamp(joinTimestamp, false))
        return config as InternalAxiosRequestConfig;
      }
      // 兼容restful风格
      config.url = config.url + params + `${joinRequestTimestamp(joinTimestamp, true)}`
      config.params = undefined
      return config as InternalAxiosRequestConfig;
    }
    if (!isString(params)) {
      formatDate && formatRequestDate(params);
      if (Reflect.has(config, 'data') && config.data && Object.keys(config.data).length > 0) {
        config.data = data
        config.params = params
      } else {
        config.data = params
        config.params = undefined
      }
      if (joinParams) {
        config.url = montageObjConvParams(
          config.url as string,
          Object.assign({}, config.params, config.data)
        )
      }
      return config as InternalAxiosRequestConfig;
    }
    // 兼容restful风格
    config.url = config.url + params
    config.params = undefined
    return config as InternalAxiosRequestConfig;
  }
  ///////////////////////////////////////////////////////////
  transfromResponse = (response: AxiosResponse<Result>, options: RequestOptions) => {
    const code = response.data.code
    const { succeedTips, failedTips } = options
    if(ResponseCode.ACCE === code && succeedTips) {
      window.$message.success(succeedTips)
    } else if(ResponseCode.REFU == code && failedTips) {
      window.$message.info(failedTips)
    }
    console.debug(response);
    switch(options.responseModel) {
      case 'native' : return response
      case 'data':    return response.data
      case 'payload': return response.data.payload
    }
  }
  ///////////////////////////////////////////////////////////
  requestInterceptor = (config: AxiosRequestConfig, options: AxiosRequestOptions) => {
    const token = AccountStore.user.webtoken
    if (token && (config as Recordable)?.requestOptions?.withToken !== false) {
      // jwt token
      (config as Recordable).headers.Authorization = options.authenticationScheme ?
        `${options.authenticationScheme} ${token}` : token
    }
    return config as InternalAxiosRequestConfig
  }
  ///////////////////////////////////////////////////////////
  responseInterceptorErrorHandler = (error: AxiosError) => {
    const data = error.response?.data
    const status = error.response?.status
    const message = error.message
    const errorStr: string = error.toString()
    try {
      if (errorStr && errorStr.includes('Network Error')) {
        window.$dialog.info({
          title: 'Network Error',
          content: '请检查您的网络连接是否正常',
          positiveText: 'OK',
          negativeText: 'Cancel',
          closable: false,
          maskClosable: false,
          onPositiveClick: () => {},
          onNegativeClick: () => {},
        })
        return Promise.reject(error)
      }
    } catch (error) {
      throw new Error(error as any)
    }
    if(!axios.isCancel(error)) {
      console.error(error);
      if(status !== StatusCode.OK || !data) {
        window.$message.error(`HttpError: ${message}`)
        return Promise.reject({code: ResponseCode.REFU})
      }
    }
  }
}

const defaultTransport = new DefaultTransport()

function createAxiosAgent(options?: Partial<AxiosRequestOptions>) {
  let _options = deepMerge<AxiosRequestOptions>({
    timeout: 60 * 1000,
    headers: { 'Content-Type': ContentType.JSON },
    axiosTransport: defaultTransport,
    authenticationScheme: '',
    requestOptions: {
      apiurl: '/',
      prefix: '',
      joinParams: false,
      joinPrefix: true,
      joinTimestamp: true,
      formatDate: true,
      responseModel: 'data',
      ignoreCancelToken: true,
      withToken: true,
    },
    withCredentials: false,
  }, options || {})
  return new AxiosAgent(_options)
}

const env = import.meta.env

export const gatewayClient = createAxiosAgent({
  requestOptions: {
    apiurl: env.PROD ? `http://${env.VITE_API_SERVER}/gateway` : "/gateway",
    prefix: "/api",
  },
});

export const engineClient = createAxiosAgent({
  requestOptions: {
    apiurl: env.PROD ? `http://${env.VITE_API_SERVER}/engine`: "/engine",
    prefix: "/api",
  },
});
