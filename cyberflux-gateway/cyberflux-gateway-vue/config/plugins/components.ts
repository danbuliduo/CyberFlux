import componentsPlugin from 'unplugin-vue-components/vite'
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers'

export function configComponentsPlugin() {
  return componentsPlugin({
    dirs: ['src/components'],
    resolvers: [
      NaiveUiResolver()
    ],
    dts: 'config/types/components.d.ts',
    extensions: ['vue'],  // 组件的有效文件扩展名。
    directoryAsNamespace: false, // 允许子目录作为组件的命名空间前缀。
    deep: true,
  })
}
