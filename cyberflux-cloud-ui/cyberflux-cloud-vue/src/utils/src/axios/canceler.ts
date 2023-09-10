import type { AxiosRequestConfig, Canceler } from 'axios'
import axios from 'axios'
import qs from 'qs'
import { isFunction } from '@/utils'

export const getPendingUrl = (config: AxiosRequestConfig) => [
  config.method, config.url, qs.stringify(config.data), qs.stringify(config.params)
].join('&')

export class AxiosCanceler {
  static pendings = new Map<string, Canceler>()
  /**
   * 添加请求
   * @param {Object} config
   */
  public append(config: AxiosRequestConfig): void {
    this.remove(config)
    const url = getPendingUrl(config)
    config.cancelToken = config.cancelToken || new axios.CancelToken(cancel => {
      if (!AxiosCanceler.pendings.has(url)) {
        AxiosCanceler.pendings.set(url, cancel)
      }
    })
  }

  /**
   * @description: 清空所有pending
   */
  public removeAll(): void {
    AxiosCanceler.pendings.forEach(cancel => {
      cancel && isFunction(cancel) && cancel()
    })
    AxiosCanceler.pendings.clear()
  }

  /**
   * 移除请求
   * @param {Object} config
   */
  public remove(config: AxiosRequestConfig): void {
    const url = getPendingUrl(config)
    if (AxiosCanceler.pendings.has(url)) {
      // 如果在 pending 中存在当前请求标识，需要取消当前请求，并且移除
      const cancel = AxiosCanceler.pendings.get(url)
      cancel && cancel(url)
      AxiosCanceler.pendings.delete(url)
    }
  }

  /**
   * @description: 重置
   */
  public reset(): void {
    AxiosCanceler.pendings = new Map<string, Canceler>()
  }
}
