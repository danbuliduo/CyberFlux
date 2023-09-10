import type { AxiosRequestConfig, AxiosInstance, AxiosResponse } from 'axios'
import type { RequestOptions, Result, UploadFileParams } from './types'
import type { AxiosRequestOptions } from './transport'
import axios from 'axios'
import { cloneDeep } from 'lodash-es'
import { AxiosCanceler } from './canceler'
import { isFunction } from '../determine.util'
import { Method, ContentType } from '@/enums'

export class AxiosAgent {

  private instance: AxiosInstance
  private options: AxiosRequestOptions

  constructor(options: AxiosRequestOptions) {
    this.instance = axios.create(options)
    this.options = options
    this.setupInterceptors()
  }

  public getInstance(): AxiosInstance {
    return this.instance
  }

  /**
   * @description: 重新配置axios
   */
  public updateOptions(options: AxiosRequestOptions): void {
    if (!this.instance) return
    this.instance = axios.create(options)
  }

  /**
   * @description: 设置通用header
   */
  public setHeader(headers: any): void {
    if (!this.instance) return
    Object.assign(this.instance.defaults.headers, headers)
  }

  /**
   * @description:   请求方法
   */
  public request<T = any>(config: AxiosRequestConfig, options?: RequestOptions): Promise<T> {
    const { axiosTransport, requestOptions } = this.options
    let _config: AxiosRequestConfig = cloneDeep(config)
    let _options: RequestOptions = Object.assign({}, requestOptions, options)

    const {
      transfromRequest, transfromResponse, requestErrorHandler
    } = axiosTransport || {}

    if (transfromRequest && isFunction(transfromRequest)) {
      _config = transfromRequest(_config, _options)
    }

    // @ts-ignore
    _config.requestOptions = _options

    return new Promise((resolve, reject) => {
      this.instance.request<any, AxiosResponse<Result>>(_config)
        .then((response: AxiosResponse<Result>) => {
          // 请求是否被取消
          const isCancel = axios.isCancel(response)
          if (transfromResponse && isFunction(transfromResponse) && !isCancel) {
            try {
              resolve(transfromResponse(response, _options))
            } catch (error) {
              reject(error || new Error('Request Error!'))
            }
            return
          }
          resolve(response as unknown as Promise<T>)
        }).catch((error: Error) => {
          if (requestErrorHandler && isFunction(requestErrorHandler)) {
            reject(requestErrorHandler(error))
            return
          }
          reject(error)
        })
    })
  }


    /**
   * @description:  文件上传
   */
  public uploadFile<T = any>(config: AxiosRequestConfig, params: UploadFileParams) {
    const formData = new window.FormData()
    const customFilename = params.name || 'file'

    formData.append(customFilename, params.file, params.filename)

    if (params.data) {
      Object.keys(params.data).forEach((key) => {
        const value = params.data![key]
        if (Array.isArray(value)) {
          value.forEach(item => {
            formData.append(`${key}[]`, item)
          })
          return
        }
        formData.append(key, params.data![key])
      })
    }

    return this.instance.request<T>({
      method: Method.POST,
      data: formData,
      headers: {
        'Content-type': ContentType.FORM_DATA,
        ignoreCancelToken: true,
      },
      ...config,
    })
  }


  /**
   * @description: 拦截器配置
   */
  private setupInterceptors(): void {
    const { axiosTransport } = this.options

    if (!axiosTransport) return

    const {
      requestInterceptor, requestInterceptorErrorHandler,
      responseInterceptor, responseInterceptorErrorHandler,
    } = axiosTransport

    // 请求拦截器配置处理
    this.instance.interceptors.request.use((config: AxiosRequestConfig) => {
      if (requestInterceptor && isFunction(requestInterceptor)) {
        config = requestInterceptor(config, this.options)
      }
      return config
    }, undefined)

    // 请求拦截器错误捕获
    requestInterceptorErrorHandler && isFunction(requestInterceptorErrorHandler) &&
    this.instance.interceptors.request.use(undefined, requestInterceptorErrorHandler)

    // 响应结果拦截器处理
    this.instance.interceptors.response.use((response: AxiosResponse<any>) => {
      response && new AxiosCanceler().remove(response.config)
      if (responseInterceptor && isFunction(responseInterceptor)) {
        response = responseInterceptor(response)
      }
      return response
    }, undefined)

    // 响应结果拦截器错误捕获
    responseInterceptorErrorHandler && isFunction(responseInterceptorErrorHandler) &&
    this.instance.interceptors.response.use(undefined, responseInterceptorErrorHandler)
  }
}
