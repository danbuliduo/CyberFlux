// 判断是否 url
export function isUrl(url: string) {
  return /^(http|https):\/\//g.test(url)
}

/**
 * 将对象添加当作参数拼接到URL上面
 * @param url 需要拼接的url
 * @param obj 参数对象
 * @returns {string} 拼接后的对象
 * 例子:
 *  let obj = {a: '3', b: '4'}
 *  setObjToUrlParams('www.baidu.com', obj)
 *  ==>www.baidu.com?a=3&b=4
 */
export function montageObjConvParams(url: string, obj: any): string {
  let parameters = ''
  for (const key in obj) {
    parameters += key + '=' + encodeURIComponent(obj[key]) + '&'
  }
  parameters = parameters.replace(/&$/, '')
  return (/\?$/.test(url)) ? url + parameters : url.replace(/\/?$/, '?') + parameters
}



export const getHost = (url: string) => {
  if(url[-1] !== '/') {
    url += "/";
  }
  let value = /^http(s)?:\/\/(.*?)\//.exec(url);
  console.log(value)
  if (value && value[2]) {
    return value[2]
  }
  return ''
};
