import vuePlugin from '@vitejs/plugin-vue'
import legacyPlugin from '@vitejs/plugin-legacy'
import setupExtendPlugin from 'vite-plugin-vue-setup-extend'

//import packageConfigPlugin from 'vite-plugin-package-config'
//import optimizePersistPlugin from 'vite-plugin-optimize-persist'

import { configAutoImportPlugin } from './auto-import'
import { configComponentsPlugin } from './components'
import { configCompressionPlugin } from './compression'
import { configImageminPlugin } from './imagemin'
import { configStyleImportPlugin } from './style-import'

export function createVitePlugins(isBuild: boolean)  {
  const plugins = [
    vuePlugin(),
    setupExtendPlugin(),
    legacyPlugin({
      targets: ['defaults', 'not IE 11'],
      additionalLegacyPolyfills: ['regenerator-runtime/runtime'],
    }),
    //packageConfigPlugin,
    //optimizePersistPlugin,
  ]
  // unplugin-auto-import
  plugins.push(configAutoImportPlugin())
  // unplugin-vue-components
  plugins.push(configComponentsPlugin())
  // vite-plugin-style-import
  plugins.push(configStyleImportPlugin())
  if(isBuild) {
    // vite-plugin-compression
    plugins.push(configCompressionPlugin())
    // vite-plugin-imagemin
    plugins.push(configImageminPlugin())
  }
  return plugins
}
