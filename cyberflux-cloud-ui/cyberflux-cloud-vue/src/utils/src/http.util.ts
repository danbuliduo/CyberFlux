import type { RequestOptions, Result, AxiosRequestOptions } from './axios';
import type { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios'
import axios  from 'axios'
import {
  AxiosAgent, AxiosTransport, deepMerge, formatRequestDate, joinRequestTimestamp
} from './axios'
import { isUrl, montageObjConvParams } from './url.util'
import { isString } from './determine.util'
import { Method, StatusCode, ServiceCode, ContentType } from '@/enums'
import { useAccountStoreWidthOut } from '~/src/store/modules/account'

class DefaultTransport extends AxiosTransport
{
  ///////////////////////////////////////////////////////////
  transfromRequest = (config: AxiosRequestConfig, options: RequestOptions) => {
    console.log("transfromRequest")
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
        return config
      }
      // 兼容restful风格
      config.url = config.url + params + `${joinRequestTimestamp(joinTimestamp, true)}`
      config.params = undefined
      return config
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
      return config
    }
    // 兼容restful风格
    config.url = config.url + params
    config.params = undefined
    return config
  }
  ///////////////////////////////////////////////////////////
  transfromResponse = (response: AxiosResponse<Result>, options: RequestOptions) => {
    console.log("transfromResponse")
    const code = response.data.code
    const { succeedTips, failedTips } = options
    if(ServiceCode.SUCCEED === code && succeedTips) {
      window.$message.success(succeedTips)
    } else if(ServiceCode.FAILED == code && failedTips) {
      window.$message.info(failedTips)
    }
    switch(options.responseModel) {
      case 'native' : return response
      case 'data':    return response.data
      case 'payload': return response.data.payload
    }
  }
  ///////////////////////////////////////////////////////////
  requestInterceptor = (config: AxiosRequestConfig, options: AxiosRequestOptions) => {
    console.log("responseInterceptor")
    const accountStore = useAccountStoreWidthOut()
    const token = accountStore.getWebToken
    console.log(token)
    if (token && (config as Recordable)?.requestOptions?.withToken !== false) {
      // jwt token
      (config as Recordable).headers.Authorization = options.authenticationScheme ?
        `${options.authenticationScheme} ${token}` : token
    }
    return config
  }
  ///////////////////////////////////////////////////////////
  responseInterceptorErrorHandler = (error: AxiosError) => {
    console.log("requestInterceptorErrorHandler", error)
    const data =error.response?.data
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
      if(status !== StatusCode.OK || !data) {
        window.$message.error(`HttpError: ${message}`)
        throw new Error(`HttpError: ${message}`)
      }
    }
  }
}

const defaultTransport = new DefaultTransport()

function createAxiosAgent(options?: Partial<AxiosRequestOptions>) {
  let _options = deepMerge<AxiosRequestOptions>({
    timeout: 10 * 1000,
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
      responseModel: 'native',
      ignoreCancelToken: true,
      withToken: true,
    },
    withCredentials: false,
  }, options || {})
  return new AxiosAgent(_options)
}

export const api = createAxiosAgent({
  requestOptions: {
    apiurl: '/api',
    prefix: '/api'
  }
})
