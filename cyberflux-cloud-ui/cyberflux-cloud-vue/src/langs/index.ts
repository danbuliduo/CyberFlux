import type { App } from 'vue'
import { createI18n } from 'vue-i18n'
import { Lang, I18N_LANG_KEY } from '@/enums'

import en_US from './en/en-US'
import ja_JP from './ja/ja-JP'
import ru_RU from './ru/ru-RU'
import zh_CN from './zh/zh-CN'
import zh_TC from './zh/zh-TC'

const i18n = createI18n({
  legacy: false,
  allowComposition: true,
  globalInjection: true,
  locale: localStorage.getItem(I18N_LANG_KEY) || undefined,
  fallbackLocale: [Lang.ZH_CN],
  messages: {
    [Lang.EN_US]: en_US,
    [Lang.JA_JP]: ja_JP,
    [Lang.RU_RU]: ru_RU,
    [Lang.ZH_CN]: zh_CN,
    [Lang.ZH_TC]: zh_TC,
  }
})


export function setupI18n(app: App) {
  app.use(i18n)
}

export default i18n
