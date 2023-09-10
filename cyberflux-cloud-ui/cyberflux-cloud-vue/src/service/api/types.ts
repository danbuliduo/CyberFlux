export interface AccountResponse<T = any> {
  code: number
  message: string
  result: T
}
