import { UserConfig, ConfigEnv } from 'vite'

import {
  createViteBuild,
  createVitePlugins,
  createViteResolve
} from './config'

export default ({ command, mode }: ConfigEnv): UserConfig => {
  const isBuild = command === 'build'
  return {
    build: createViteBuild(isBuild),
    plugins: createVitePlugins(isBuild),
    resolve: createViteResolve(),
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "@/assets/style/var.scss";'
        }
      }
    },
    server: {
      host: true,
      port: 18080,
    },
  }
}
