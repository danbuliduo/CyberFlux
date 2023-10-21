/**
 * @brief 请求方法
 */
export enum Method {
  GET    = 'get',
  POST   = 'post',
  PATCH  = 'patch',
  PUT    = 'put',
  DELETE = 'delete',
}

/**
 * @brief 响应状态吗
 */
export enum StatusCode {
  OK                    = 200,
  NOT_FOUND             = 404,
  METHOD_NOT_ALLOWED    = 405,
  INTERNAL_SERVER_ERROR = 500,
  NOT_ACCEPTABLE        = 406,
  TIME_OUT              = 10042,
}

export enum ResponseCode {
  ACCE =  1,
  WARN =  0,
  REFU = -1,
}

/**
 * @brief 常用的contentTyp类型
 */
export enum ContentType {
  // json
  JSON = 'application/json;charset=UTF-8',
  // text
  TEXT = 'text/plain;charset=UTF-8',
  // form-data 一般配合qs
  FORM_URLENCODED = 'application/x-www-form-urlencoded;charset=UTF-8',
  // form-data  上传
  FORM_DATA = 'multipart/form-data;charset=UTF-8',
}
