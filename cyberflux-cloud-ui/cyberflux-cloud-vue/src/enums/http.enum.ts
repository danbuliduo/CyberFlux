/**
 * @description: 请求方法
 */
export enum Method {
  GET = 'get',
  POST = 'post',
  PATCH = 'PATCH',
  PUT = 'PUT',
  DELETE = 'DELETE',
}

/**
 * @description: 响应状态吗
 */
export enum StatusCode {
  OK = 200,
  NOT_FOUND = 404,
  METHOD_NOT_ALLOWED = 405,
  INTERNAL_SERVER_ERROR = 500,
  TIME_OUT = 10042,
}