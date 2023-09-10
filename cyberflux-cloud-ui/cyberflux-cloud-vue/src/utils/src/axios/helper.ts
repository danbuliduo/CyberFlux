import { isObject, isString } from '../determine.util'

export function joinRequestTimestamp<T extends boolean>(join: boolean | undefined, restful: T): T extends true ? string : object

export function joinRequestTimestamp(join: boolean | undefined, restful = false): string | object {
  if (!join) return restful ? '' : {}
  const now = new Date().getTime()
  return restful ? `?_t=${now}` : { _t: now }
}

/**
 * @description: 格式化请求参数时间
 */
export function formatRequestDate(params: Recordable, format: string = 'YYYY-MM-DD HH:mm') {
  if (Object.prototype.toString.call(params) !== '[object Object]') {
    return
  }

  for (const key in params) {
    if (params[key] && params[key]._isAMomentObject) {
      params[key] = params[key].format(format)
    }
    if (isString(key)) {
      const value = params[key]
      if (value) {
        try {
          params[key] = isString(value) ? value.trim() : value;
        } catch (error) {
          throw new Error(error as any)
        }
      }
    }
    if (isObject(params[key])) {
      formatRequestDate(params[key])
    }
  }
}
export function deepMerge<T = any>(src: any = {}, target: any = {}): T {
  let key: string
  for (key in target) {
    src[key] = isObject(src[key]) ? deepMerge(src[key], target[key]) : (src[key] = target[key])
  }
  return src
}
