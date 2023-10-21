import { UserConfig, ConfigEnv, loadEnv } from "vite";
import {createViteBuild, createVitePlugins, createViteResolve} from './config';

export default ({ command, mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd()) as unknown  as ViteMetaEnv;
  const isBuild = command === 'build';
  return {
    base: "./",
    build: createViteBuild(isBuild),
    plugins: createVitePlugins(isBuild),
    resolve: createViteResolve(),
    server: {
      host: "0.0.0.0",
      port: 18080,
      https: false,
      proxy: {
        "/gateway": {
          target: `http://${env.VITE_API_SERVER}`,
          changeOrigin: true,
          //rewrite: (path) => path.replace(/^\/gateway/, ""),
        },
        "/engine": {
          target: `http://${env.VITE_API_SERVER}`,
          changeOrigin: true,
          //rewrite: (path) => path.replace(/^\/engine/, ""),
        },
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "@/assets/style/var.scss";',
        },
      },
    },
    optimizeDeps: {},
  };
}
