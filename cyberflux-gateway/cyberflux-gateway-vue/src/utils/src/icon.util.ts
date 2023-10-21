import { NIcon } from 'naive-ui'

/**
 * @brief render 图标
 * */
export function renderIcon(icon: Component) {
  return () => h(NIcon, null, { default: () => h(icon) });
}
/**
 * @brief font 图标(Font class)
 * */
export function renderFontClassIcon(icon: string, name = 'iconfont') {
  return () => h('span', { class: [name, icon] });
}
/**
 * @brief font 图标(Unicode)
 * */
export function renderUnicodeIcon(icon: string, name = 'iconfont') {
  return () => h('span', { class: [name], innerHTML: icon });
}
/**
 * @brief font svg 图标
 * */
export function renderFontSvg(icon: any) {
  return () => h(NIcon, null, {
    default: () => h(
      'svg', { class: 'icon', 'aria-hidden': 'true' }, h('use', { 'xlink:href': '#${icon}' })
    ),
  });
}
