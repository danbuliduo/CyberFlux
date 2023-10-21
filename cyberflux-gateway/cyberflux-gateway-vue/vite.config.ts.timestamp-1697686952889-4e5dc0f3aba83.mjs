// vite.config.ts
import { loadEnv } from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite@4.4.9_sass@1.66.1_terser@5.21.0/node_modules/vite/dist/node/index.js";

// config/build/index.ts
function createViteBuild(isBuild) {
  return {
    /**
     * 类型： string | string[]
     * 默认: modules
     * 设置最终构建的浏览器兼容目标
     */
    target: "es2020",
    /**
     * 类型： string
     * 默认: dist
     * 指定输出路径（相对于 项目根目录）
     */
    outDir: "dist",
    /**
     * 类型： string
     * 默认: assets
     * 指定生成静态资源（js、css、img、fonts）的存放路径（相对于 build.outDir）。
     */
    assetsDir: "assets",
    /**
     * 类型： number
     * 默认： 4096 (4kb)
     * 小于此阈值的导入或引用资源将内联为 base64 编码，以避免额外的 http 请求。设置为 0 可以完全禁用此项。
     */
    assetsInlineLimit: 4096,
    /**
     * 类型： boolean
     * 默认: true
     * 启用/禁用 CSS 代码拆分。如果禁用，整个项目中的所有 CSS 将被提取到一个 CSS 文件中
     */
    cssCodeSplit: true,
    /**
     * 类型： boolean | 'inline' | 'hidden'
     * 默认： false
     * 构建后是否生成 source map 文件。如果为 true，将会创建一个独立的 source map 文件。
     * 如果为 'inline' source map 将作为一个 data URI 附加在输出文件中。
     * 如果为 'hidden' 的工作原理与 'true' 相似，只是 bundle 文件中相应的注释将不被保留。
     */
    sourcemap: false,
    /**
     * 类型： RollupOptions
     * 自定义底层的 Rollup 打包配置。这与从 Rollup 配置文件导出的选项相同，并将与 Vite 的内部 Rollup 选项合并。
     */
    rollupOptions: {
      // 确保外部化处理那些你不想打包进库的依赖
      external: [],
      // 指定文件输出的配置
      output: {
        chunkFileNames: "assets/js/[name]-[hash].js",
        entryFileNames: "assets/js/[name]-[hash].js",
        assetFileNames: "assets/[ext]/[name]-[hash].[ext]",
        manualChunks(id) {
          if (id.includes("node_modules")) {
            return "vendor";
          }
        }
      }
    },
    /*terserOptions: {
      // 打包后移除console和注释
      compress: {
        keep_infinity: true,
        drop_console: isBuild,
        drop_debugger: isBuild,
        pure_funcs: ['console.log', 'console.info'],
      },
    },*/
    /**
     * 类型： boolean
     * 默认： true
     * 启用/禁用 brotli 压缩大小报告。压缩大型输出文件可能会很慢，因此禁用该功能可能会提高大型项目的构建性能。
     */
    brotliSize: false,
    /**
     * 类型： number
     * 默认: 500
     * chunk 大小警告的限制（以 kbs 为单位）。
     */
    chunkSizeWarningLimit: 2e3
  };
}

// config/plugins/index.ts
import vuePlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-vue@4.3.4_vite@4.4.9_vue@3.3.4/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import vueJsxPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-vue-jsx@3.0.2_vite@4.4.9_vue@3.3.4/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import legacyPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-legacy@4.1.1_terser@5.21.0_vite@4.4.9/node_modules/@vitejs/plugin-legacy/dist/index.mjs";
import setupExtendPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-vue-setup-extend@0.4.0_vite@4.4.9/node_modules/vite-plugin-vue-setup-extend/dist/index.mjs";

// config/plugins/auto-import.ts
import autoImportPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-auto-import@0.15.3_@vueuse+core@10.4.1/node_modules/unplugin-auto-import/dist/vite.js";
function configAutoImportPlugin() {
  return autoImportPlugin({
    imports: [
      "vue"
    ],
    dts: "config/types/auto-imports.d.ts"
  });
}

// config/plugins/components.ts
import componentsPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-vue-components@0.24.1_vue@3.3.4/node_modules/unplugin-vue-components/dist/vite.mjs";
import { NaiveUiResolver } from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-vue-components@0.24.1_vue@3.3.4/node_modules/unplugin-vue-components/dist/resolvers.mjs";
function configComponentsPlugin() {
  return componentsPlugin({
    dirs: ["src/components"],
    resolvers: [
      NaiveUiResolver()
    ],
    dts: "config/types/components.d.ts",
    extensions: ["vue"],
    // 组件的有效文件扩展名。
    directoryAsNamespace: false,
    // 允许子目录作为组件的命名空间前缀。
    deep: true
  });
}

// config/plugins/compression.ts
import compressionPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-compression@0.5.1_vite@4.4.9/node_modules/vite-plugin-compression/dist/index.mjs";
function configCompressionPlugin(deleteOriginFile = false) {
  return compressionPlugin({
    disable: false,
    verbose: false,
    threshold: 10240,
    algorithm: "gzip",
    ext: ".gz",
    deleteOriginFile
  });
}

// config/plugins/imagemin.ts
import imageminPlugin from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-imagemin@0.6.1_vite@4.4.9/node_modules/vite-plugin-imagemin/dist/index.mjs";
function configImageminPlugin() {
  return imageminPlugin({
    gifsicle: {
      optimizationLevel: 7,
      interlaced: false
    },
    optipng: {
      optimizationLevel: 7
    },
    mozjpeg: {
      quality: 20
    },
    pngquant: {
      quality: [0.8, 0.9],
      speed: 4
    },
    svgo: {
      plugins: [
        {
          name: "removeViewBox"
        },
        {
          name: "removeEmptyAttrs",
          active: false
        }
      ]
    }
  });
}

// config/plugins/html.ts
import { createHtmlPlugin } from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-html@3.2.0_vite@4.4.9/node_modules/vite-plugin-html/dist/index.mjs";

// package.json
var package_default = {
  name: "cyberflux-cloud-gateway",
  private: true,
  version: "0.0.1",
  type: "module",
  license: "MIT",
  author: {
    name: "ming",
    email: "3524529163@qq.com"
  },
  resolutions: {
    "bin-wrapper": "npm:bin-wrapper-china"
  },
  scripts: {
    bootstrap: "pnpm install",
    build: "vue-tsc && vite build",
    serve: "vite",
    dev: "vite --mode dev",
    prod: "vite --mode prod",
    test: "vite --mode test",
    preview: "vite preview"
  },
  dependencies: {
    "@highlightjs/vue-plugin": "^2.1.0",
    "@vicons/antd": "^0.12.0",
    "@vicons/ionicons5": "^0.12.0",
    "@vueuse/core": "^10.4.1",
    axios: "^1.5.1",
    buffer: "^6.0.3",
    highcharts: "^10.3.3",
    "highcharts-vue": "^1.4.3",
    "highlight.js": "^11.9.0",
    idb: "^7.1.1",
    "lint-staged": "^13.3.0",
    "lodash-es": "^4.17.21",
    mqtt: "^4.3.7",
    "naive-ui": "^2.34.4",
    pinia: "^2.1.6",
    qs: "^6.11.2",
    vfonts: "^0.0.3",
    vue: "^3.3.4",
    "vue-i18n": "^9.2.2",
    "vue-router": "^4.2.4",
    "vue-timeago3": "^2.3.1",
    "vue-types": "^5.1.1",
    "vue3-hotkey": "^1.0.3",
    vuedraggable: "^4.1.0"
  },
  devDependencies: {
    "@intlify/vite-plugin-vue-i18n": "^7.0.0",
    "@types/lodash-es": "^4.17.9",
    "@types/three": "^0.152.1",
    "@typescript-eslint/eslint-plugin": "^5.62.0",
    "@typescript-eslint/parser": "^5.62.0",
    "@vitejs/plugin-legacy": "^4.1.1",
    "@vitejs/plugin-vue": "^4.3.4",
    "@vitejs/plugin-vue-jsx": "^3.0.2",
    autoprefixer: "^10.4.15",
    consola: "^3.2.3",
    "core-js": "^3.32.1",
    eslint: "^8.48.0",
    "eslint-define-config": "^1.23.0",
    "eslint-plugin-vue": "^9.17.0",
    postcss: "^8.4.29",
    "postcss-html": "^1.5.0",
    "postcss-scss": "^4.0.7",
    "resize-observer-polyfill": "^1.5.1",
    sass: "^1.66.1",
    "sass-loader": "^13.3.2",
    stylelint: "^15.10.3",
    "stylelint-config-prettier": "^9.0.5",
    "stylelint-config-recommended-scss": "^10.0.0",
    "stylelint-config-standard": "^32.0.0",
    "stylelint-config-standard-vue": "^1.0.0",
    "stylelint-order": "^6.0.3",
    "stylelint-scss": "^4.7.0",
    tailwindcss: "^3.3.3",
    typescript: "^4.9.5",
    "unplugin-auto-import": "^0.15.3",
    "unplugin-vue-components": "^0.24.1",
    vite: "^4.4.9",
    "vite-plugin-compression": "^0.5.1",
    "vite-plugin-html": "^3.2.0",
    "vite-plugin-imagemin": "^0.6.1",
    "vite-plugin-optimize-persist": "^0.1.2",
    "vite-plugin-package-config": "^0.1.1",
    "vite-plugin-style-import": "^2.0.0",
    "vite-plugin-vue-setup-extend": "^0.4.0",
    "vue-global-api": "^0.4.1",
    "vue-tsc": "^1.8.8"
  },
  vite: {
    optimizeDeps: {
      include: [
        "vue",
        "vue-global-api"
      ]
    }
  },
  "lint-staged": {
    "*.{vue,js,ts,tsx}": "eslint --fix"
  }
};

// config/plugins/html.ts
var GLOB_CONFIG_FILE_NAME = "app.config.js";
var VITE_APP_TITLE = "CyberFlux Cloud";
var VITE_PUBLIC_PATH = "/";
function configHtmlPlugin(isBuild) {
  const path = VITE_PUBLIC_PATH.endsWith("/") ? VITE_PUBLIC_PATH : `${VITE_PUBLIC_PATH}/`;
  const getAppConfigSrc = () => {
    return `${path || "/"}${GLOB_CONFIG_FILE_NAME}?v=${package_default.version}-${(/* @__PURE__ */ new Date()).getTime()}`;
  };
  const htmlPlugin = createHtmlPlugin({
    minify: isBuild,
    inject: {
      // Inject data into ejs template
      data: {
        title: VITE_APP_TITLE
      },
      // Embed the generated app.config.js file
      tags: isBuild ? [{
        tag: "script",
        attrs: {
          src: getAppConfigSrc()
        }
      }] : []
    }
  });
  return htmlPlugin;
}

// config/plugins/style-import.ts
import { createStyleImportPlugin } from "file:///D:/TengMing/CyberFlux/cyberflux-gateway/cyberflux-gateway-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-style-import@2.0.0_vite@4.4.9/node_modules/vite-plugin-style-import/dist/index.mjs";
function configStyleImportPlugin() {
  return createStyleImportPlugin({
    resolves: []
  });
}

// config/plugins/index.ts
function createVitePlugins(isBuild) {
  const plugins = [
    vuePlugin(),
    vueJsxPlugin(),
    setupExtendPlugin(),
    legacyPlugin({
      targets: ["defaults", "not IE 11"],
      additionalLegacyPolyfills: ["regenerator-runtime/runtime"]
    })
    //packageConfigPlugin,
    //optimizePersistPlugin,
  ];
  plugins.push(configAutoImportPlugin());
  plugins.push(configComponentsPlugin());
  plugins.push(configHtmlPlugin(isBuild));
  plugins.push(configStyleImportPlugin());
  if (isBuild) {
    plugins.push(configCompressionPlugin());
    plugins.push(configImageminPlugin());
  }
  return plugins;
}

// config/resolve/index.ts
import { resolve } from "path";
function createViteResolve() {
  return {
    alias: {
      "~": resolve(process.cwd(), "."),
      "@": resolve(process.cwd(), "src"),
      "#": resolve(process.cwd(), "types"),
      "@library": resolve(process.cwd(), "library"),
      "vue-i18n": "vue-i18n/dist/vue-i18n.cjs.js",
      // 解决wran
      "mqtt": "mqtt/dist/mqtt.js"
    },
    // https://cn.vitejs.dev/config/#resolve-extensions
    extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"]
  };
}

// vite.config.ts
var vite_config_default = ({ command, mode }) => {
  const env = loadEnv(mode, process.cwd());
  const isBuild = command === "build";
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
          changeOrigin: true
          //rewrite: (path) => path.replace(/^\/gateway/, ""),
        },
        "/engine": {
          target: `http://${env.VITE_API_SERVER}`,
          changeOrigin: true
          //rewrite: (path) => path.replace(/^\/engine/, ""),
        }
      }
    },
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "@/assets/style/var.scss";'
        }
      }
    },
    optimizeDeps: {}
  };
};
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiLCAiY29uZmlnL2J1aWxkL2luZGV4LnRzIiwgImNvbmZpZy9wbHVnaW5zL2luZGV4LnRzIiwgImNvbmZpZy9wbHVnaW5zL2F1dG8taW1wb3J0LnRzIiwgImNvbmZpZy9wbHVnaW5zL2NvbXBvbmVudHMudHMiLCAiY29uZmlnL3BsdWdpbnMvY29tcHJlc3Npb24udHMiLCAiY29uZmlnL3BsdWdpbnMvaW1hZ2VtaW4udHMiLCAiY29uZmlnL3BsdWdpbnMvaHRtbC50cyIsICJwYWNrYWdlLmpzb24iLCAiY29uZmlnL3BsdWdpbnMvc3R5bGUtaW1wb3J0LnRzIiwgImNvbmZpZy9yZXNvbHZlL2luZGV4LnRzIl0sCiAgInNvdXJjZXNDb250ZW50IjogWyJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFx2aXRlLmNvbmZpZy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvQ3liZXJGbHV4L2N5YmVyZmx1eC1nYXRld2F5L2N5YmVyZmx1eC1nYXRld2F5LXZ1ZS92aXRlLmNvbmZpZy50c1wiO2ltcG9ydCB7IFVzZXJDb25maWcsIENvbmZpZ0VudiwgbG9hZEVudiB9IGZyb20gXCJ2aXRlXCI7XG5pbXBvcnQge2NyZWF0ZVZpdGVCdWlsZCwgY3JlYXRlVml0ZVBsdWdpbnMsIGNyZWF0ZVZpdGVSZXNvbHZlfSBmcm9tICcuL2NvbmZpZyc7XG5cbmV4cG9ydCBkZWZhdWx0ICh7IGNvbW1hbmQsIG1vZGUgfTogQ29uZmlnRW52KTogVXNlckNvbmZpZyA9PiB7XG4gIGNvbnN0IGVudiA9IGxvYWRFbnYobW9kZSwgcHJvY2Vzcy5jd2QoKSkgYXMgdW5rbm93biAgYXMgVml0ZU1ldGFFbnY7XG4gIGNvbnN0IGlzQnVpbGQgPSBjb21tYW5kID09PSAnYnVpbGQnO1xuICByZXR1cm4ge1xuICAgIGJhc2U6IFwiLi9cIixcbiAgICBidWlsZDogY3JlYXRlVml0ZUJ1aWxkKGlzQnVpbGQpLFxuICAgIHBsdWdpbnM6IGNyZWF0ZVZpdGVQbHVnaW5zKGlzQnVpbGQpLFxuICAgIHJlc29sdmU6IGNyZWF0ZVZpdGVSZXNvbHZlKCksXG4gICAgc2VydmVyOiB7XG4gICAgICBob3N0OiBcIjAuMC4wLjBcIixcbiAgICAgIHBvcnQ6IDE4MDgwLFxuICAgICAgaHR0cHM6IGZhbHNlLFxuICAgICAgcHJveHk6IHtcbiAgICAgICAgXCIvZ2F0ZXdheVwiOiB7XG4gICAgICAgICAgdGFyZ2V0OiBgaHR0cDovLyR7ZW52LlZJVEVfQVBJX1NFUlZFUn1gLFxuICAgICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSxcbiAgICAgICAgICAvL3Jld3JpdGU6IChwYXRoKSA9PiBwYXRoLnJlcGxhY2UoL15cXC9nYXRld2F5LywgXCJcIiksXG4gICAgICAgIH0sXG4gICAgICAgIFwiL2VuZ2luZVwiOiB7XG4gICAgICAgICAgdGFyZ2V0OiBgaHR0cDovLyR7ZW52LlZJVEVfQVBJX1NFUlZFUn1gLFxuICAgICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSxcbiAgICAgICAgICAvL3Jld3JpdGU6IChwYXRoKSA9PiBwYXRoLnJlcGxhY2UoL15cXC9lbmdpbmUvLCBcIlwiKSxcbiAgICAgICAgfSxcbiAgICAgIH0sXG4gICAgfSxcbiAgICBjc3M6IHtcbiAgICAgIHByZXByb2Nlc3Nvck9wdGlvbnM6IHtcbiAgICAgICAgc2Nzczoge1xuICAgICAgICAgIGphdmFzY3JpcHRFbmFibGVkOiB0cnVlLFxuICAgICAgICAgIGFkZGl0aW9uYWxEYXRhOiAnQGltcG9ydCBcIkAvYXNzZXRzL3N0eWxlL3Zhci5zY3NzXCI7JyxcbiAgICAgICAgfSxcbiAgICAgIH0sXG4gICAgfSxcbiAgICBvcHRpbWl6ZURlcHM6IHt9LFxuICB9O1xufVxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxDeWJlckZsdXhcXFxcY3liZXJmbHV4LWdhdGV3YXlcXFxcY3liZXJmbHV4LWdhdGV3YXktdnVlXFxcXGNvbmZpZ1xcXFxidWlsZFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFxjb25maWdcXFxcYnVpbGRcXFxcaW5kZXgudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L1RlbmdNaW5nL0N5YmVyRmx1eC9jeWJlcmZsdXgtZ2F0ZXdheS9jeWJlcmZsdXgtZ2F0ZXdheS12dWUvY29uZmlnL2J1aWxkL2luZGV4LnRzXCI7ZXhwb3J0IGZ1bmN0aW9uIGNyZWF0ZVZpdGVCdWlsZChpc0J1aWxkOiBib29sZWFuKSB7XG4gIHJldHVybiB7XG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIHN0cmluZyB8IHN0cmluZ1tdXG4gICAgICogXHU5RUQ4XHU4QkE0OiBtb2R1bGVzXG4gICAgICogXHU4QkJFXHU3RjZFXHU2NzAwXHU3RUM4XHU2Nzg0XHU1RUZBXHU3Njg0XHU2RDRGXHU4OUM4XHU1NjY4XHU1MTdDXHU1QkI5XHU3NkVFXHU2ODA3XG4gICAgICovXG4gICAgdGFyZ2V0OiBcImVzMjAyMFwiLFxuICAgIC8qKlxuICAgICAqIFx1N0M3Qlx1NTc4Qlx1RkYxQSBzdHJpbmdcbiAgICAgKiBcdTlFRDhcdThCQTQ6IGRpc3RcbiAgICAgKiBcdTYzMDdcdTVCOUFcdThGOTNcdTUxRkFcdThERUZcdTVGODRcdUZGMDhcdTc2RjhcdTVCRjlcdTRFOEUgXHU5ODc5XHU3NkVFXHU2ODM5XHU3NkVFXHU1RjU1XHVGRjA5XG4gICAgICovXG4gICAgb3V0RGlyOiBcImRpc3RcIixcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgc3RyaW5nXG4gICAgICogXHU5RUQ4XHU4QkE0OiBhc3NldHNcbiAgICAgKiBcdTYzMDdcdTVCOUFcdTc1MUZcdTYyMTBcdTk3NTlcdTYwMDFcdThENDRcdTZFOTBcdUZGMDhqc1x1MzAwMWNzc1x1MzAwMWltZ1x1MzAwMWZvbnRzXHVGRjA5XHU3Njg0XHU1QjU4XHU2NTNFXHU4REVGXHU1Rjg0XHVGRjA4XHU3NkY4XHU1QkY5XHU0RThFIGJ1aWxkLm91dERpclx1RkYwOVx1MzAwMlxuICAgICAqL1xuICAgIGFzc2V0c0RpcjogXCJhc3NldHNcIixcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgbnVtYmVyXG4gICAgICogXHU5RUQ4XHU4QkE0XHVGRjFBIDQwOTYgKDRrYilcbiAgICAgKiBcdTVDMEZcdTRFOEVcdTZCNjRcdTk2MDhcdTUwM0NcdTc2ODRcdTVCRkNcdTUxNjVcdTYyMTZcdTVGMTVcdTc1MjhcdThENDRcdTZFOTBcdTVDMDZcdTUxODVcdTgwNTRcdTRFM0EgYmFzZTY0IFx1N0YxNlx1NzgwMVx1RkYwQ1x1NEVFNVx1OTA3Rlx1NTE0RFx1OTg5RFx1NTkxNlx1NzY4NCBodHRwIFx1OEJGN1x1NkM0Mlx1MzAwMlx1OEJCRVx1N0Y2RVx1NEUzQSAwIFx1NTNFRlx1NEVFNVx1NUI4Q1x1NTE2OFx1Nzk4MVx1NzUyOFx1NkI2NFx1OTg3OVx1MzAwMlxuICAgICAqL1xuICAgIGFzc2V0c0lubGluZUxpbWl0OiA0MDk2LFxuICAgIC8qKlxuICAgICAqIFx1N0M3Qlx1NTc4Qlx1RkYxQSBib29sZWFuXG4gICAgICogXHU5RUQ4XHU4QkE0OiB0cnVlXG4gICAgICogXHU1NDJGXHU3NTI4L1x1Nzk4MVx1NzUyOCBDU1MgXHU0RUUzXHU3ODAxXHU2MkM2XHU1MjA2XHUzMDAyXHU1OTgyXHU2NzlDXHU3OTgxXHU3NTI4XHVGRjBDXHU2NTc0XHU0RTJBXHU5ODc5XHU3NkVFXHU0RTJEXHU3Njg0XHU2MjQwXHU2NzA5IENTUyBcdTVDMDZcdTg4QUJcdTYzRDBcdTUzRDZcdTUyMzBcdTRFMDBcdTRFMkEgQ1NTIFx1NjU4N1x1NEVGNlx1NEUyRFxuICAgICAqL1xuICAgIGNzc0NvZGVTcGxpdDogdHJ1ZSxcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgYm9vbGVhbiB8ICdpbmxpbmUnIHwgJ2hpZGRlbidcbiAgICAgKiBcdTlFRDhcdThCQTRcdUZGMUEgZmFsc2VcbiAgICAgKiBcdTY3ODRcdTVFRkFcdTU0MEVcdTY2MkZcdTU0MjZcdTc1MUZcdTYyMTAgc291cmNlIG1hcCBcdTY1ODdcdTRFRjZcdTMwMDJcdTU5ODJcdTY3OUNcdTRFM0EgdHJ1ZVx1RkYwQ1x1NUMwNlx1NEYxQVx1NTIxQlx1NUVGQVx1NEUwMFx1NEUyQVx1NzJFQ1x1N0FDQlx1NzY4NCBzb3VyY2UgbWFwIFx1NjU4N1x1NEVGNlx1MzAwMlxuICAgICAqIFx1NTk4Mlx1Njc5Q1x1NEUzQSAnaW5saW5lJyBzb3VyY2UgbWFwIFx1NUMwNlx1NEY1Q1x1NEUzQVx1NEUwMFx1NEUyQSBkYXRhIFVSSSBcdTk2NDRcdTUyQTBcdTU3MjhcdThGOTNcdTUxRkFcdTY1ODdcdTRFRjZcdTRFMkRcdTMwMDJcbiAgICAgKiBcdTU5ODJcdTY3OUNcdTRFM0EgJ2hpZGRlbicgXHU3Njg0XHU1REU1XHU0RjVDXHU1MzlGXHU3NDA2XHU0RTBFICd0cnVlJyBcdTc2RjhcdTRGM0NcdUZGMENcdTUzRUFcdTY2MkYgYnVuZGxlIFx1NjU4N1x1NEVGNlx1NEUyRFx1NzZGOFx1NUU5NFx1NzY4NFx1NkNFOFx1OTFDQVx1NUMwNlx1NEUwRFx1ODhBQlx1NEZERFx1NzU1OVx1MzAwMlxuICAgICAqL1xuICAgIHNvdXJjZW1hcDogZmFsc2UsXG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIFJvbGx1cE9wdGlvbnNcbiAgICAgKiBcdTgxRUFcdTVCOUFcdTRFNDlcdTVFOTVcdTVDNDJcdTc2ODQgUm9sbHVwIFx1NjI1M1x1NTMwNVx1OTE0RFx1N0Y2RVx1MzAwMlx1OEZEOVx1NEUwRVx1NEVDRSBSb2xsdXAgXHU5MTREXHU3RjZFXHU2NTg3XHU0RUY2XHU1QkZDXHU1MUZBXHU3Njg0XHU5MDA5XHU5ODc5XHU3NkY4XHU1NDBDXHVGRjBDXHU1RTc2XHU1QzA2XHU0RTBFIFZpdGUgXHU3Njg0XHU1MTg1XHU5MEU4IFJvbGx1cCBcdTkwMDlcdTk4NzlcdTU0MDhcdTVFNzZcdTMwMDJcbiAgICAgKi9cbiAgICByb2xsdXBPcHRpb25zOiB7XG4gICAgICAvLyBcdTc4NkVcdTRGRERcdTU5MTZcdTkwRThcdTUzMTZcdTU5MDRcdTc0MDZcdTkwQTNcdTRFOUJcdTRGNjBcdTRFMERcdTYwRjNcdTYyNTNcdTUzMDVcdThGREJcdTVFOTNcdTc2ODRcdTRGOURcdThENTZcbiAgICAgIGV4dGVybmFsOiBbXSxcbiAgICAgIC8vIFx1NjMwN1x1NUI5QVx1NjU4N1x1NEVGNlx1OEY5M1x1NTFGQVx1NzY4NFx1OTE0RFx1N0Y2RVxuICAgICAgb3V0cHV0OiB7XG4gICAgICAgIGNodW5rRmlsZU5hbWVzOiBcImFzc2V0cy9qcy9bbmFtZV0tW2hhc2hdLmpzXCIsXG4gICAgICAgIGVudHJ5RmlsZU5hbWVzOiBcImFzc2V0cy9qcy9bbmFtZV0tW2hhc2hdLmpzXCIsXG4gICAgICAgIGFzc2V0RmlsZU5hbWVzOiBcImFzc2V0cy9bZXh0XS9bbmFtZV0tW2hhc2hdLltleHRdXCIsXG4gICAgICAgIG1hbnVhbENodW5rcyhpZDogYW55KSB7XG4gICAgICAgICAgaWYgKGlkLmluY2x1ZGVzKFwibm9kZV9tb2R1bGVzXCIpKSB7XG4gICAgICAgICAgICByZXR1cm4gXCJ2ZW5kb3JcIjsgLy9cdTRFRTNcdTc4MDFcdTUyMDZcdTUyNzJcdTRFM0FcdTdCMkNcdTRFMDlcdTY1QjlcdTUzMDVcbiAgICAgICAgICB9XG4gICAgICAgIH0sXG4gICAgICB9LFxuICAgIH0sXG4gICAgLyp0ZXJzZXJPcHRpb25zOiB7XG4gICAgICAvLyBcdTYyNTNcdTUzMDVcdTU0MEVcdTc5RkJcdTk2NjRjb25zb2xlXHU1NDhDXHU2Q0U4XHU5MUNBXG4gICAgICBjb21wcmVzczoge1xuICAgICAgICBrZWVwX2luZmluaXR5OiB0cnVlLFxuICAgICAgICBkcm9wX2NvbnNvbGU6IGlzQnVpbGQsXG4gICAgICAgIGRyb3BfZGVidWdnZXI6IGlzQnVpbGQsXG4gICAgICAgIHB1cmVfZnVuY3M6IFsnY29uc29sZS5sb2cnLCAnY29uc29sZS5pbmZvJ10sXG4gICAgICB9LFxuICAgIH0sKi9cbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgYm9vbGVhblxuICAgICAqIFx1OUVEOFx1OEJBNFx1RkYxQSB0cnVlXG4gICAgICogXHU1NDJGXHU3NTI4L1x1Nzk4MVx1NzUyOCBicm90bGkgXHU1MzhCXHU3RjI5XHU1OTI3XHU1QzBGXHU2MkE1XHU1NDRBXHUzMDAyXHU1MzhCXHU3RjI5XHU1OTI3XHU1NzhCXHU4RjkzXHU1MUZBXHU2NTg3XHU0RUY2XHU1M0VGXHU4MEZEXHU0RjFBXHU1Rjg4XHU2MTYyXHVGRjBDXHU1NkUwXHU2QjY0XHU3OTgxXHU3NTI4XHU4QkU1XHU1MjlGXHU4MEZEXHU1M0VGXHU4MEZEXHU0RjFBXHU2M0QwXHU5QUQ4XHU1OTI3XHU1NzhCXHU5ODc5XHU3NkVFXHU3Njg0XHU2Nzg0XHU1RUZBXHU2MDI3XHU4MEZEXHUzMDAyXG4gICAgICovXG4gICAgYnJvdGxpU2l6ZTogZmFsc2UsXG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIG51bWJlclxuICAgICAqIFx1OUVEOFx1OEJBNDogNTAwXG4gICAgICogY2h1bmsgXHU1OTI3XHU1QzBGXHU4QjY2XHU1NDRBXHU3Njg0XHU5NjUwXHU1MjM2XHVGRjA4XHU0RUU1IGticyBcdTRFM0FcdTUzNTVcdTRGNERcdUZGMDlcdTMwMDJcbiAgICAgKi9cbiAgICBjaHVua1NpemVXYXJuaW5nTGltaXQ6IDIwMDAsXG4gIH07XG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcaW5kZXgudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L1RlbmdNaW5nL0N5YmVyRmx1eC9jeWJlcmZsdXgtZ2F0ZXdheS9jeWJlcmZsdXgtZ2F0ZXdheS12dWUvY29uZmlnL3BsdWdpbnMvaW5kZXgudHNcIjtpbXBvcnQgdnVlUGx1Z2luIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSdcbmltcG9ydCB2dWVKc3hQbHVnaW4gZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlLWpzeCdcbmltcG9ydCBsZWdhY3lQbHVnaW4gZnJvbSAnQHZpdGVqcy9wbHVnaW4tbGVnYWN5J1xuaW1wb3J0IHNldHVwRXh0ZW5kUGx1Z2luIGZyb20gJ3ZpdGUtcGx1Z2luLXZ1ZS1zZXR1cC1leHRlbmQnXG5cbi8vaW1wb3J0IHBhY2thZ2VDb25maWdQbHVnaW4gZnJvbSAndml0ZS1wbHVnaW4tcGFja2FnZS1jb25maWcnXG4vL2ltcG9ydCBvcHRpbWl6ZVBlcnNpc3RQbHVnaW4gZnJvbSAndml0ZS1wbHVnaW4tb3B0aW1pemUtcGVyc2lzdCdcblxuaW1wb3J0IHsgY29uZmlnQXV0b0ltcG9ydFBsdWdpbiB9IGZyb20gJy4vYXV0by1pbXBvcnQnXG5pbXBvcnQgeyBjb25maWdDb21wb25lbnRzUGx1Z2luIH0gZnJvbSAnLi9jb21wb25lbnRzJ1xuaW1wb3J0IHsgY29uZmlnQ29tcHJlc3Npb25QbHVnaW4gfSBmcm9tICcuL2NvbXByZXNzaW9uJ1xuaW1wb3J0IHsgY29uZmlnSW1hZ2VtaW5QbHVnaW4gfSBmcm9tICcuL2ltYWdlbWluJ1xuaW1wb3J0IHsgY29uZmlnSHRtbFBsdWdpbiB9IGZyb20gJy4vaHRtbCdcbmltcG9ydCB7IGNvbmZpZ1N0eWxlSW1wb3J0UGx1Z2luIH0gZnJvbSAnLi9zdHlsZS1pbXBvcnQnXG5cbmV4cG9ydCBmdW5jdGlvbiBjcmVhdGVWaXRlUGx1Z2lucyhpc0J1aWxkOiBib29sZWFuKSAge1xuICBjb25zdCBwbHVnaW5zOiBhbnlbXSA9IFtcbiAgICB2dWVQbHVnaW4oKSxcbiAgICB2dWVKc3hQbHVnaW4oKSxcbiAgICBzZXR1cEV4dGVuZFBsdWdpbigpLFxuICAgIGxlZ2FjeVBsdWdpbih7XG4gICAgICB0YXJnZXRzOiBbJ2RlZmF1bHRzJywgJ25vdCBJRSAxMSddLFxuICAgICAgYWRkaXRpb25hbExlZ2FjeVBvbHlmaWxsczogWydyZWdlbmVyYXRvci1ydW50aW1lL3J1bnRpbWUnXSxcbiAgICB9KSxcbiAgICAvL3BhY2thZ2VDb25maWdQbHVnaW4sXG4gICAgLy9vcHRpbWl6ZVBlcnNpc3RQbHVnaW4sXG4gIF1cblxuXG4gIC8vIHVucGx1Z2luLWF1dG8taW1wb3J0XG4gIHBsdWdpbnMucHVzaChjb25maWdBdXRvSW1wb3J0UGx1Z2luKCkpXG4gIC8vIHVucGx1Z2luLXZ1ZS1jb21wb25lbnRzXG4gIHBsdWdpbnMucHVzaChjb25maWdDb21wb25lbnRzUGx1Z2luKCkpXG4gIC8vIHZvdGUtcGx1Z2luLWh0bWxcbiAgcGx1Z2lucy5wdXNoKGNvbmZpZ0h0bWxQbHVnaW4oaXNCdWlsZCkpXG4gIC8vIHZpdGUtcGx1Z2luLXN0eWxlLWltcG9ydFxuICBwbHVnaW5zLnB1c2goY29uZmlnU3R5bGVJbXBvcnRQbHVnaW4oKSlcbiAgaWYoaXNCdWlsZCkge1xuICAgIC8vIHZpdGUtcGx1Z2luLWNvbXByZXNzaW9uXG4gICAgcGx1Z2lucy5wdXNoKGNvbmZpZ0NvbXByZXNzaW9uUGx1Z2luKCkpXG4gICAgLy8gdml0ZS1wbHVnaW4taW1hZ2VtaW5cbiAgICBwbHVnaW5zLnB1c2goY29uZmlnSW1hZ2VtaW5QbHVnaW4oKSlcbiAgfVxuICByZXR1cm4gcGx1Z2luc1xufVxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxDeWJlckZsdXhcXFxcY3liZXJmbHV4LWdhdGV3YXlcXFxcY3liZXJmbHV4LWdhdGV3YXktdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxDeWJlckZsdXhcXFxcY3liZXJmbHV4LWdhdGV3YXlcXFxcY3liZXJmbHV4LWdhdGV3YXktdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXFxcXGF1dG8taW1wb3J0LnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9UZW5nTWluZy9DeWJlckZsdXgvY3liZXJmbHV4LWdhdGV3YXkvY3liZXJmbHV4LWdhdGV3YXktdnVlL2NvbmZpZy9wbHVnaW5zL2F1dG8taW1wb3J0LnRzXCI7aW1wb3J0IGF1dG9JbXBvcnRQbHVnaW4gZnJvbSAndW5wbHVnaW4tYXV0by1pbXBvcnQvdml0ZSdcblxuZXhwb3J0IGZ1bmN0aW9uIGNvbmZpZ0F1dG9JbXBvcnRQbHVnaW4oKSB7XG4gIHJldHVybiBhdXRvSW1wb3J0UGx1Z2luKHtcbiAgICBpbXBvcnRzOiBbXG4gICAgICAndnVlJyxcbiAgICBdLFxuICAgIGR0czogJ2NvbmZpZy90eXBlcy9hdXRvLWltcG9ydHMuZC50cycsXG4gIH0pXG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcY29tcG9uZW50cy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvQ3liZXJGbHV4L2N5YmVyZmx1eC1nYXRld2F5L2N5YmVyZmx1eC1nYXRld2F5LXZ1ZS9jb25maWcvcGx1Z2lucy9jb21wb25lbnRzLnRzXCI7aW1wb3J0IGNvbXBvbmVudHNQbHVnaW4gZnJvbSAndW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvdml0ZSdcbmltcG9ydCB7IE5haXZlVWlSZXNvbHZlciB9IGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3Jlc29sdmVycydcblxuZXhwb3J0IGZ1bmN0aW9uIGNvbmZpZ0NvbXBvbmVudHNQbHVnaW4oKSB7XG4gIHJldHVybiBjb21wb25lbnRzUGx1Z2luKHtcbiAgICBkaXJzOiBbJ3NyYy9jb21wb25lbnRzJ10sXG4gICAgcmVzb2x2ZXJzOiBbXG4gICAgICBOYWl2ZVVpUmVzb2x2ZXIoKVxuICAgIF0sXG4gICAgZHRzOiAnY29uZmlnL3R5cGVzL2NvbXBvbmVudHMuZC50cycsXG4gICAgZXh0ZW5zaW9uczogWyd2dWUnXSwgIC8vIFx1N0VDNFx1NEVGNlx1NzY4NFx1NjcwOVx1NjU0OFx1NjU4N1x1NEVGNlx1NjI2OVx1NUM1NVx1NTQwRFx1MzAwMlxuICAgIGRpcmVjdG9yeUFzTmFtZXNwYWNlOiBmYWxzZSwgLy8gXHU1MTQxXHU4QkI4XHU1QjUwXHU3NkVFXHU1RjU1XHU0RjVDXHU0RTNBXHU3RUM0XHU0RUY2XHU3Njg0XHU1NDdEXHU1NDBEXHU3QTdBXHU5NUY0XHU1MjREXHU3RjAwXHUzMDAyXG4gICAgZGVlcDogdHJ1ZSxcbiAgfSlcbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1xcXFxjb21wcmVzc2lvbi50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvQ3liZXJGbHV4L2N5YmVyZmx1eC1nYXRld2F5L2N5YmVyZmx1eC1nYXRld2F5LXZ1ZS9jb25maWcvcGx1Z2lucy9jb21wcmVzc2lvbi50c1wiO2ltcG9ydCBjb21wcmVzc2lvblBsdWdpbiBmcm9tICd2aXRlLXBsdWdpbi1jb21wcmVzc2lvbidcblxuZXhwb3J0IGZ1bmN0aW9uIGNvbmZpZ0NvbXByZXNzaW9uUGx1Z2luKGRlbGV0ZU9yaWdpbkZpbGUgOiBib29sZWFuID0gZmFsc2UpIHtcbiAgcmV0dXJuIGNvbXByZXNzaW9uUGx1Z2luKHtcbiAgICBkaXNhYmxlOiBmYWxzZSxcbiAgICB2ZXJib3NlOiBmYWxzZSxcbiAgICB0aHJlc2hvbGQ6IDEwMjQwLFxuICAgIGFsZ29yaXRobTogJ2d6aXAnLFxuICAgIGV4dDogJy5neicsXG4gICAgZGVsZXRlT3JpZ2luRmlsZSxcbiAgfSlcbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcQ3liZXJGbHV4XFxcXGN5YmVyZmx1eC1nYXRld2F5XFxcXGN5YmVyZmx1eC1nYXRld2F5LXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1xcXFxpbWFnZW1pbi50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvQ3liZXJGbHV4L2N5YmVyZmx1eC1nYXRld2F5L2N5YmVyZmx1eC1nYXRld2F5LXZ1ZS9jb25maWcvcGx1Z2lucy9pbWFnZW1pbi50c1wiO2ltcG9ydCBpbWFnZW1pblBsdWdpbiBmcm9tICd2aXRlLXBsdWdpbi1pbWFnZW1pbidcclxuXHJcbmV4cG9ydCBmdW5jdGlvbiBjb25maWdJbWFnZW1pblBsdWdpbigpIHtcclxuICByZXR1cm4gaW1hZ2VtaW5QbHVnaW4oe1xyXG4gICAgZ2lmc2ljbGU6IHtcclxuICAgICAgb3B0aW1pemF0aW9uTGV2ZWw6IDcsXHJcbiAgICAgIGludGVybGFjZWQ6IGZhbHNlLFxyXG4gICAgfSxcclxuICAgIG9wdGlwbmc6IHtcclxuICAgICAgb3B0aW1pemF0aW9uTGV2ZWw6IDcsXHJcbiAgICB9LFxyXG4gICAgbW96anBlZzoge1xyXG4gICAgICBxdWFsaXR5OiAyMCxcclxuICAgIH0sXHJcbiAgICBwbmdxdWFudDoge1xyXG4gICAgICBxdWFsaXR5OiBbMC44LCAwLjldLFxyXG4gICAgICBzcGVlZDogNCxcclxuICAgIH0sXHJcbiAgICBzdmdvOiB7XHJcbiAgICAgIHBsdWdpbnM6IFtcclxuICAgICAgICB7XHJcbiAgICAgICAgICBuYW1lOiAncmVtb3ZlVmlld0JveCcsXHJcbiAgICAgICAgfSxcclxuICAgICAgICB7XHJcbiAgICAgICAgICBuYW1lOiAncmVtb3ZlRW1wdHlBdHRycycsXHJcbiAgICAgICAgICBhY3RpdmU6IGZhbHNlLFxyXG4gICAgICAgIH0sXHJcbiAgICAgIF0sXHJcbiAgICB9LFxyXG4gIH0pXHJcbn0iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcaHRtbC50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvQ3liZXJGbHV4L2N5YmVyZmx1eC1nYXRld2F5L2N5YmVyZmx1eC1nYXRld2F5LXZ1ZS9jb25maWcvcGx1Z2lucy9odG1sLnRzXCI7LyoqXG4gKiBQbHVnaW4gdG8gbWluaW1pemUgYW5kIHVzZSBlanMgdGVtcGxhdGUgc3ludGF4IGluIGluZGV4Lmh0bWwuXG4gKiBodHRwczovL2dpdGh1Yi5jb20vYW5uY3diL3ZpdGUtcGx1Z2luLWh0bWxcbiAqL1xuaW1wb3J0IHsgY3JlYXRlSHRtbFBsdWdpbiB9IGZyb20gJ3ZpdGUtcGx1Z2luLWh0bWwnXG5pbXBvcnQgcGtnIGZyb20gJy4uLy4uL3BhY2thZ2UuanNvbidcblxuY29uc3QgR0xPQl9DT05GSUdfRklMRV9OQU1FID0gJ2FwcC5jb25maWcuanMnXG5jb25zdCBWSVRFX0FQUF9USVRMRSA9ICdDeWJlckZsdXggQ2xvdWQnXG5jb25zdCBWSVRFX1BVQkxJQ19QQVRIID0nLydcblxuZXhwb3J0IGZ1bmN0aW9uIGNvbmZpZ0h0bWxQbHVnaW4oaXNCdWlsZDogYm9vbGVhbikge1xuXG4gIGNvbnN0IHBhdGggPSBWSVRFX1BVQkxJQ19QQVRILmVuZHNXaXRoKCcvJykgPyBWSVRFX1BVQkxJQ19QQVRIIDogYCR7VklURV9QVUJMSUNfUEFUSH0vYDtcblxuICBjb25zdCBnZXRBcHBDb25maWdTcmMgPSAoKSA9PiB7XG4gICAgcmV0dXJuIGAke3BhdGggfHwgJy8nfSR7R0xPQl9DT05GSUdfRklMRV9OQU1FfT92PSR7cGtnLnZlcnNpb259LSR7bmV3IERhdGUoKS5nZXRUaW1lKCl9YDtcbiAgfTtcblxuICBjb25zdCBodG1sUGx1Z2luID0gY3JlYXRlSHRtbFBsdWdpbih7XG4gICAgbWluaWZ5OiBpc0J1aWxkLFxuICAgIGluamVjdDoge1xuICAgICAgLy8gSW5qZWN0IGRhdGEgaW50byBlanMgdGVtcGxhdGVcbiAgICAgIGRhdGE6IHtcbiAgICAgICAgdGl0bGU6IFZJVEVfQVBQX1RJVExFLFxuICAgICAgfSxcbiAgICAgIC8vIEVtYmVkIHRoZSBnZW5lcmF0ZWQgYXBwLmNvbmZpZy5qcyBmaWxlXG4gICAgICB0YWdzOiBpc0J1aWxkID8gW3tcbiAgICAgICAgdGFnOiAnc2NyaXB0JyxcbiAgICAgICAgYXR0cnM6IHtcbiAgICAgICAgICBzcmM6IGdldEFwcENvbmZpZ1NyYygpLFxuICAgICAgICB9XG4gICAgICB9XSA6IFtdLFxuICAgIH0sXG4gIH0pO1xuICByZXR1cm4gaHRtbFBsdWdpbjtcbn1cbiIsICJ7XG4gIFwibmFtZVwiOiBcImN5YmVyZmx1eC1jbG91ZC1nYXRld2F5XCIsXG4gIFwicHJpdmF0ZVwiOiB0cnVlLFxuICBcInZlcnNpb25cIjogXCIwLjAuMVwiLFxuICBcInR5cGVcIjogXCJtb2R1bGVcIixcbiAgXCJsaWNlbnNlXCI6IFwiTUlUXCIsXG4gIFwiYXV0aG9yXCI6IHtcbiAgICBcIm5hbWVcIjogXCJtaW5nXCIsXG4gICAgXCJlbWFpbFwiOiBcIjM1MjQ1MjkxNjNAcXEuY29tXCJcbiAgfSxcbiAgXCJyZXNvbHV0aW9uc1wiOiB7XG4gICAgXCJiaW4td3JhcHBlclwiOiBcIm5wbTpiaW4td3JhcHBlci1jaGluYVwiXG4gIH0sXG4gIFwic2NyaXB0c1wiOiB7XG4gICAgXCJib290c3RyYXBcIjogXCJwbnBtIGluc3RhbGxcIixcbiAgICBcImJ1aWxkXCI6IFwidnVlLXRzYyAmJiB2aXRlIGJ1aWxkXCIsXG4gICAgXCJzZXJ2ZVwiOiBcInZpdGVcIixcbiAgICBcImRldlwiOiBcInZpdGUgLS1tb2RlIGRldlwiLFxuICAgIFwicHJvZFwiOiBcInZpdGUgLS1tb2RlIHByb2RcIixcbiAgICBcInRlc3RcIjogXCJ2aXRlIC0tbW9kZSB0ZXN0XCIsXG4gICAgXCJwcmV2aWV3XCI6IFwidml0ZSBwcmV2aWV3XCJcbiAgfSxcbiAgXCJkZXBlbmRlbmNpZXNcIjoge1xuICAgIFwiQGhpZ2hsaWdodGpzL3Z1ZS1wbHVnaW5cIjogXCJeMi4xLjBcIixcbiAgICBcIkB2aWNvbnMvYW50ZFwiOiBcIl4wLjEyLjBcIixcbiAgICBcIkB2aWNvbnMvaW9uaWNvbnM1XCI6IFwiXjAuMTIuMFwiLFxuICAgIFwiQHZ1ZXVzZS9jb3JlXCI6IFwiXjEwLjQuMVwiLFxuICAgIFwiYXhpb3NcIjogXCJeMS41LjFcIixcbiAgICBcImJ1ZmZlclwiOiBcIl42LjAuM1wiLFxuICAgIFwiaGlnaGNoYXJ0c1wiOiBcIl4xMC4zLjNcIixcbiAgICBcImhpZ2hjaGFydHMtdnVlXCI6IFwiXjEuNC4zXCIsXG4gICAgXCJoaWdobGlnaHQuanNcIjogXCJeMTEuOS4wXCIsXG4gICAgXCJpZGJcIjogXCJeNy4xLjFcIixcbiAgICBcImxpbnQtc3RhZ2VkXCI6IFwiXjEzLjMuMFwiLFxuICAgIFwibG9kYXNoLWVzXCI6IFwiXjQuMTcuMjFcIixcbiAgICBcIm1xdHRcIjogXCJeNC4zLjdcIixcbiAgICBcIm5haXZlLXVpXCI6IFwiXjIuMzQuNFwiLFxuICAgIFwicGluaWFcIjogXCJeMi4xLjZcIixcbiAgICBcInFzXCI6IFwiXjYuMTEuMlwiLFxuICAgIFwidmZvbnRzXCI6IFwiXjAuMC4zXCIsXG4gICAgXCJ2dWVcIjogXCJeMy4zLjRcIixcbiAgICBcInZ1ZS1pMThuXCI6IFwiXjkuMi4yXCIsXG4gICAgXCJ2dWUtcm91dGVyXCI6IFwiXjQuMi40XCIsXG4gICAgXCJ2dWUtdGltZWFnbzNcIjogXCJeMi4zLjFcIixcbiAgICBcInZ1ZS10eXBlc1wiOiBcIl41LjEuMVwiLFxuICAgIFwidnVlMy1ob3RrZXlcIjogXCJeMS4wLjNcIixcbiAgICBcInZ1ZWRyYWdnYWJsZVwiOiBcIl40LjEuMFwiXG4gIH0sXG4gIFwiZGV2RGVwZW5kZW5jaWVzXCI6IHtcbiAgICBcIkBpbnRsaWZ5L3ZpdGUtcGx1Z2luLXZ1ZS1pMThuXCI6IFwiXjcuMC4wXCIsXG4gICAgXCJAdHlwZXMvbG9kYXNoLWVzXCI6IFwiXjQuMTcuOVwiLFxuICAgIFwiQHR5cGVzL3RocmVlXCI6IFwiXjAuMTUyLjFcIixcbiAgICBcIkB0eXBlc2NyaXB0LWVzbGludC9lc2xpbnQtcGx1Z2luXCI6IFwiXjUuNjIuMFwiLFxuICAgIFwiQHR5cGVzY3JpcHQtZXNsaW50L3BhcnNlclwiOiBcIl41LjYyLjBcIixcbiAgICBcIkB2aXRlanMvcGx1Z2luLWxlZ2FjeVwiOiBcIl40LjEuMVwiLFxuICAgIFwiQHZpdGVqcy9wbHVnaW4tdnVlXCI6IFwiXjQuMy40XCIsXG4gICAgXCJAdml0ZWpzL3BsdWdpbi12dWUtanN4XCI6IFwiXjMuMC4yXCIsXG4gICAgXCJhdXRvcHJlZml4ZXJcIjogXCJeMTAuNC4xNVwiLFxuICAgIFwiY29uc29sYVwiOiBcIl4zLjIuM1wiLFxuICAgIFwiY29yZS1qc1wiOiBcIl4zLjMyLjFcIixcbiAgICBcImVzbGludFwiOiBcIl44LjQ4LjBcIixcbiAgICBcImVzbGludC1kZWZpbmUtY29uZmlnXCI6IFwiXjEuMjMuMFwiLFxuICAgIFwiZXNsaW50LXBsdWdpbi12dWVcIjogXCJeOS4xNy4wXCIsXG4gICAgXCJwb3N0Y3NzXCI6IFwiXjguNC4yOVwiLFxuICAgIFwicG9zdGNzcy1odG1sXCI6IFwiXjEuNS4wXCIsXG4gICAgXCJwb3N0Y3NzLXNjc3NcIjogXCJeNC4wLjdcIixcbiAgICBcInJlc2l6ZS1vYnNlcnZlci1wb2x5ZmlsbFwiOiBcIl4xLjUuMVwiLFxuICAgIFwic2Fzc1wiOiBcIl4xLjY2LjFcIixcbiAgICBcInNhc3MtbG9hZGVyXCI6IFwiXjEzLjMuMlwiLFxuICAgIFwic3R5bGVsaW50XCI6IFwiXjE1LjEwLjNcIixcbiAgICBcInN0eWxlbGludC1jb25maWctcHJldHRpZXJcIjogXCJeOS4wLjVcIixcbiAgICBcInN0eWxlbGludC1jb25maWctcmVjb21tZW5kZWQtc2Nzc1wiOiBcIl4xMC4wLjBcIixcbiAgICBcInN0eWxlbGludC1jb25maWctc3RhbmRhcmRcIjogXCJeMzIuMC4wXCIsXG4gICAgXCJzdHlsZWxpbnQtY29uZmlnLXN0YW5kYXJkLXZ1ZVwiOiBcIl4xLjAuMFwiLFxuICAgIFwic3R5bGVsaW50LW9yZGVyXCI6IFwiXjYuMC4zXCIsXG4gICAgXCJzdHlsZWxpbnQtc2Nzc1wiOiBcIl40LjcuMFwiLFxuICAgIFwidGFpbHdpbmRjc3NcIjogXCJeMy4zLjNcIixcbiAgICBcInR5cGVzY3JpcHRcIjogXCJeNC45LjVcIixcbiAgICBcInVucGx1Z2luLWF1dG8taW1wb3J0XCI6IFwiXjAuMTUuM1wiLFxuICAgIFwidW5wbHVnaW4tdnVlLWNvbXBvbmVudHNcIjogXCJeMC4yNC4xXCIsXG4gICAgXCJ2aXRlXCI6IFwiXjQuNC45XCIsXG4gICAgXCJ2aXRlLXBsdWdpbi1jb21wcmVzc2lvblwiOiBcIl4wLjUuMVwiLFxuICAgIFwidml0ZS1wbHVnaW4taHRtbFwiOiBcIl4zLjIuMFwiLFxuICAgIFwidml0ZS1wbHVnaW4taW1hZ2VtaW5cIjogXCJeMC42LjFcIixcbiAgICBcInZpdGUtcGx1Z2luLW9wdGltaXplLXBlcnNpc3RcIjogXCJeMC4xLjJcIixcbiAgICBcInZpdGUtcGx1Z2luLXBhY2thZ2UtY29uZmlnXCI6IFwiXjAuMS4xXCIsXG4gICAgXCJ2aXRlLXBsdWdpbi1zdHlsZS1pbXBvcnRcIjogXCJeMi4wLjBcIixcbiAgICBcInZpdGUtcGx1Z2luLXZ1ZS1zZXR1cC1leHRlbmRcIjogXCJeMC40LjBcIixcbiAgICBcInZ1ZS1nbG9iYWwtYXBpXCI6IFwiXjAuNC4xXCIsXG4gICAgXCJ2dWUtdHNjXCI6IFwiXjEuOC44XCJcbiAgfSxcbiAgXCJ2aXRlXCI6IHtcbiAgICBcIm9wdGltaXplRGVwc1wiOiB7XG4gICAgICBcImluY2x1ZGVcIjogW1xuICAgICAgICBcInZ1ZVwiLFxuICAgICAgICBcInZ1ZS1nbG9iYWwtYXBpXCJcbiAgICAgIF1cbiAgICB9XG4gIH0sXG4gIFwibGludC1zdGFnZWRcIjoge1xuICAgIFwiKi57dnVlLGpzLHRzLHRzeH1cIjogXCJlc2xpbnQgLS1maXhcIlxuICB9XG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcc3R5bGUtaW1wb3J0LnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9UZW5nTWluZy9DeWJlckZsdXgvY3liZXJmbHV4LWdhdGV3YXkvY3liZXJmbHV4LWdhdGV3YXktdnVlL2NvbmZpZy9wbHVnaW5zL3N0eWxlLWltcG9ydC50c1wiO2ltcG9ydCB7IGNyZWF0ZVN0eWxlSW1wb3J0UGx1Z2luIH0gZnJvbSAndml0ZS1wbHVnaW4tc3R5bGUtaW1wb3J0J1xuXG5leHBvcnQgZnVuY3Rpb24gY29uZmlnU3R5bGVJbXBvcnRQbHVnaW4oKSB7XG4gIHJldHVybiBjcmVhdGVTdHlsZUltcG9ydFBsdWdpbih7XG4gICAgcmVzb2x2ZXM6IFtdXG4gIH0pXG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHJlc29sdmVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXEN5YmVyRmx1eFxcXFxjeWJlcmZsdXgtZ2F0ZXdheVxcXFxjeWJlcmZsdXgtZ2F0ZXdheS12dWVcXFxcY29uZmlnXFxcXHJlc29sdmVcXFxcaW5kZXgudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L1RlbmdNaW5nL0N5YmVyRmx1eC9jeWJlcmZsdXgtZ2F0ZXdheS9jeWJlcmZsdXgtZ2F0ZXdheS12dWUvY29uZmlnL3Jlc29sdmUvaW5kZXgudHNcIjtpbXBvcnQgeyByZXNvbHZlIH0gZnJvbSAncGF0aCdcblxuZXhwb3J0IGZ1bmN0aW9uIGNyZWF0ZVZpdGVSZXNvbHZlKCkgIHtcbiAgcmV0dXJuIHtcbiAgICBhbGlhczoge1xuICAgICAgJ34nOiByZXNvbHZlKHByb2Nlc3MuY3dkKCksICcuJyksXG4gICAgICAnQCc6IHJlc29sdmUocHJvY2Vzcy5jd2QoKSwgJ3NyYycpLFxuICAgICAgJyMnOiByZXNvbHZlKHByb2Nlc3MuY3dkKCksICd0eXBlcycpLFxuICAgICAgJ0BsaWJyYXJ5JzogcmVzb2x2ZShwcm9jZXNzLmN3ZCgpLCAnbGlicmFyeScpLFxuICAgICAgJ3Z1ZS1pMThuJzogJ3Z1ZS1pMThuL2Rpc3QvdnVlLWkxOG4uY2pzLmpzJywgLy8gXHU4OUUzXHU1MUIzd3JhblxuICAgICAgJ21xdHQnOiAnbXF0dC9kaXN0L21xdHQuanMnLFxuICAgIH0sXG4gICAgLy8gaHR0cHM6Ly9jbi52aXRlanMuZGV2L2NvbmZpZy8jcmVzb2x2ZS1leHRlbnNpb25zXG4gICAgZXh0ZW5zaW9uczogWycubWpzJywgJy5qcycsICcudHMnLCAnLmpzeCcsICcudHN4JywgJy5qc29uJywgJy52dWUnXSxcbiAgfVxufVxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUFtWCxTQUFnQyxlQUFlOzs7QUNBVCxTQUFTLGdCQUFnQixTQUFrQjtBQUNsYyxTQUFPO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBTUwsUUFBUTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQSxJQU1SLFFBQVE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsSUFNUixXQUFXO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBTVgsbUJBQW1CO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBTW5CLGNBQWM7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBUWQsV0FBVztBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsSUFLWCxlQUFlO0FBQUE7QUFBQSxNQUViLFVBQVUsQ0FBQztBQUFBO0FBQUEsTUFFWCxRQUFRO0FBQUEsUUFDTixnQkFBZ0I7QUFBQSxRQUNoQixnQkFBZ0I7QUFBQSxRQUNoQixnQkFBZ0I7QUFBQSxRQUNoQixhQUFhLElBQVM7QUFDcEIsY0FBSSxHQUFHLFNBQVMsY0FBYyxHQUFHO0FBQy9CLG1CQUFPO0FBQUEsVUFDVDtBQUFBLFFBQ0Y7QUFBQSxNQUNGO0FBQUEsSUFDRjtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFB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
