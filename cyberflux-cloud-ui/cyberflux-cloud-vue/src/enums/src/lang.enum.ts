/**
 * @brief 语言映射
 */
export enum Lang {
  EN_US = 'en-US',
  JA_JP = 'ja-JP',
  RU_RU = 'ru-RU',
  ZH_CN = 'zh-CN',
  ZH_TC = 'zh-TC',
}

/**
 * @brief 标签映射
 */
export enum Lable {
  EN_US = 'English',
  JA_JP = '日本語',
  RU_RU = 'Русский',
  ZH_CN = '简体中文',
  ZH_TC = '繁體中文',
}

const langLableMap = new Map<Lang, Lable>()

langLableMap.set(Lang.EN_US, Lable.EN_US)
langLableMap.set(Lang.JA_JP, Lable.JA_JP)
langLableMap.set(Lang.RU_RU, Lable.RU_RU)
langLableMap.set(Lang.ZH_CN, Lable.ZH_CN)
langLableMap.set(Lang.ZH_TC, Lable.ZH_TC)

export { langLableMap }
