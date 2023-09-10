export interface UploadFileParams {
  // 其他参数
  data?: Recordable
  // 文件参数接口字段名
  name?: string
  // 文件
  file: File | Blob
  // 文件名称
  filename?: string | undefined
  [key: string]: any
}

export interface RequestOptions {
  apiurl?: string
  prefix?: string
  joinParams?: boolean
  joinPrefix?: boolean
  joinTimestamp?: boolean
  formatDate?: boolean
  showMessage?: boolean
  parseModel?: 'text' | 'json'
  succeedTips?: string
  failedTips?: string
  responseModel?: 'native' | 'data' | 'payload'
  ignoreCancelToken?: boolean
  widthWebToken?: boolean
}

export interface Result<T = any> {
  code?: number
  header?: any
  payload?: T
}
