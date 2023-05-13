import { resolve } from 'path'

export function createViteResolve()  {
  return {
    alias: {
      '~': resolve(process.cwd(), '.'),
      '@': resolve(process.cwd(), 'src'),
      '#': resolve(process.cwd(), 'types'),
      '@library': resolve(process.cwd(), 'library'),
      'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js', // 解决wran
      'mqtt': 'mqtt/dist/mqtt.js',
    },
    // https://cn.vitejs.dev/config/#resolve-extensions
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue'],
  }
}
