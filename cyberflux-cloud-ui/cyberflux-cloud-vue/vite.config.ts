import { UserConfig, ConfigEnv } from 'vite';

import {
  createViteBuild, createVitePlugins, createViteResolve
} from './config';

export default ({ command, mode }: ConfigEnv): UserConfig => {
  const isBuild = (command === 'build')
  return {
    base: './',
    build: createViteBuild(isBuild),
    plugins: createVitePlugins(isBuild),
    resolve: createViteResolve(),
    server: {
      host: '0.0.0.0',
      port: 18080,
    },
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "@/assets/style/var.scss";'
        }
      }
    },
    optimizeDeps: {

    },
  }
}
