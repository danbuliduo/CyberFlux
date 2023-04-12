import { createI18n } from 'vue-i18n'

import en_US from './en/en-US'
import ja_JP from './ja/ja-JP'
import ru_RU from './ru/ru-RU'
import zh_CN from './zh/zh-CN'
import zh_TC from './zh/zh-TC'

export enum Lang {
  EN_US = 'en-US',
  JA_JP = 'ja-JP',
  RU_RU = 'ru-RU',
  ZH_CN = 'zh-CN',
  ZH_TC = 'zh-TC',
}

export const LangKey = 'locale'

const i18n = createI18n({
  legacy: false,
  allowComposition: true,
  globalInjection: true,
  locale: localStorage.getItem(LangKey),
  fallbackLocale: [Lang.EN_US],
  messages: {
    [Lang.EN_US]: en_US,
    [Lang.JA_JP]: ja_JP,
    [Lang.RU_RU]: ru_RU,
    [Lang.ZH_CN]: zh_CN,
    [Lang.ZH_TC]: zh_TC,
  }
})

export default i18n