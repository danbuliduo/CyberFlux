import type { AxiosError, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig} from 'axios'
import type { RequestOptions } from './types'

export interface AxiosRequestOptions extends AxiosRequestConfig {
  axiosTransport?: AxiosTransport
  requestOptions?: RequestOptions
  authenticationScheme?: string
}

export abstract class AxiosTransport {
  /**
   * @description: 请求之前处理
   */
  transfromRequest?: (
    config: AxiosRequestConfig,
    options: RequestOptions
  ) => InternalAxiosRequestConfig;
  /**
   * @description: 请求完成处理
   */
  transfromResponse?: (response: AxiosResponse<Result>, options: RequestOptions) => any;
  /**
   * @description: 请求异常处理
   */
  requestErrorHandler?: (error: Error) => Promise<any>;
  /**
   * @description: 请求之前的拦截器
   */
  requestInterceptor?: (config: AxiosRequestConfig, options: AxiosRequestOptions) => InternalAxiosRequestConfig;
  /**
   * @description: 请求之后的拦截器
   */
  responseInterceptor?: (response: AxiosResponse<any>) => AxiosResponse<any>;
  /**
   * @description: 请求之前的拦截器错误处理
   */
  requestInterceptorErrorHandler?: (error: AxiosError) => void;
  /**
   * @description: 请求之后的拦截器错误处理
   */
  responseInterceptorErrorHandler?: (error: AxiosError) => void;
}
