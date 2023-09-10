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
import vuePlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-vue@4.3.4_vite@4.4.9_vue@3.3.4/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import vueJsxPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-vue-jsx@3.0.2_vite@4.4.9_vue@3.3.4/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import legacyPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-legacy@4.1.1_terser@5.19.3_vite@4.4.9/node_modules/@vitejs/plugin-legacy/dist/index.mjs";
import setupExtendPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-vue-setup-extend@0.4.0_vite@4.4.9/node_modules/vite-plugin-vue-setup-extend/dist/index.mjs";

// config/plugins/auto-import.ts
import autoImportPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-auto-import@0.15.3_@vueuse+core@10.4.1/node_modules/unplugin-auto-import/dist/vite.js";
function configAutoImportPlugin() {
  return autoImportPlugin({
    imports: [
      "vue"
    ],
    dts: "config/types/auto-imports.d.ts"
  });
}

// config/plugins/components.ts
import componentsPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-vue-components@0.24.1_vue@3.3.4/node_modules/unplugin-vue-components/dist/vite.mjs";
import { NaiveUiResolver } from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+unplugin-vue-components@0.24.1_vue@3.3.4/node_modules/unplugin-vue-components/dist/resolvers.mjs";
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
import compressionPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-compression@0.5.1_vite@4.4.9/node_modules/vite-plugin-compression/dist/index.mjs";
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
import imageminPlugin from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-imagemin@0.6.1_vite@4.4.9/node_modules/vite-plugin-imagemin/dist/index.mjs";
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
import { createHtmlPlugin } from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-html@3.2.0_vite@4.4.9/node_modules/vite-plugin-html/dist/index.mjs";

// package.json
var package_default = {
  name: "cyberflux-cloud",
  private: true,
  version: "Alpha",
  type: "module",
  author: {
    name: "Ming",
    email: "dm5862110@outlook.com"
  },
  resolutions: {
    "bin-wrapper": "npm:bin-wrapper-china"
  },
  scripts: {
    bootstrap: "pnpm install",
    build: "vue-tsc && vite build",
    dev: "vite",
    preview: "vite preview",
    "lint:eslint": 'eslint "{src}/**/*.{vue,ts,tsx}" --fix',
    "lint:prettier": 'prettier --write --loglevel warn "src/**/*.{js,json,tsx,css,less,scss,vue,html,md}"',
    "lint:stylelint": 'stylelint --fix "**/*.{vue,less,postcss,css,scss}" --cache --cache-location node_modules/.cache/stylelint/'
  },
  dependencies: {
    "@vicons/antd": "^0.12.0",
    "@vicons/ionicons5": "^0.12.0",
    "@vueuse/core": "^10.4.1",
    axios: "^1.2.2",
    buffer: "^6.0.3",
    highcharts: "^10.3.3",
    "highcharts-vue": "^1.4.3",
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
    "vue3-icon-picker": "^0.1.1",
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
import { createStyleImportPlugin } from "file:///D:/TengMing/Master/CyberFlux-Framework/cyberflux-cloud-ui/cyberflux-cloud-vue/node_modules/.pnpm/registry.npmmirror.com+vite-plugin-style-import@2.0.0_vite@4.4.9/node_modules/vite-plugin-style-import/dist/index.mjs";
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
        "/api": {
          target: "http://localhost:18087",
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, "")
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
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsiY29uZmlnL2J1aWxkL2luZGV4LnRzIiwgImNvbmZpZy9wbHVnaW5zL2luZGV4LnRzIiwgImNvbmZpZy9wbHVnaW5zL2F1dG8taW1wb3J0LnRzIiwgImNvbmZpZy9wbHVnaW5zL2NvbXBvbmVudHMudHMiLCAiY29uZmlnL3BsdWdpbnMvY29tcHJlc3Npb24udHMiLCAiY29uZmlnL3BsdWdpbnMvaW1hZ2VtaW4udHMiLCAiY29uZmlnL3BsdWdpbnMvaHRtbC50cyIsICJwYWNrYWdlLmpzb24iLCAiY29uZmlnL3BsdWdpbnMvc3R5bGUtaW1wb3J0LnRzIiwgImNvbmZpZy9yZXNvbHZlL2luZGV4LnRzIiwgInZpdGUuY29uZmlnLnRzIl0sCiAgInNvdXJjZXNDb250ZW50IjogWyJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXGJ1aWxkXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxcYnVpbGRcXFxcaW5kZXgudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L1RlbmdNaW5nL01hc3Rlci9DeWJlckZsdXgtRnJhbWV3b3JrL2N5YmVyZmx1eC1jbG91ZC11aS9jeWJlcmZsdXgtY2xvdWQtdnVlL2NvbmZpZy9idWlsZC9pbmRleC50c1wiO2V4cG9ydCBmdW5jdGlvbiBjcmVhdGVWaXRlQnVpbGQoaXNCdWlsZDogYm9vbGVhbikge1xuICByZXR1cm4ge1xuICAgIC8qKlxuICAgICAqIFx1N0M3Qlx1NTc4Qlx1RkYxQSBzdHJpbmcgfCBzdHJpbmdbXVxuICAgICAqIFx1OUVEOFx1OEJBNDogbW9kdWxlc1xuICAgICAqIFx1OEJCRVx1N0Y2RVx1NjcwMFx1N0VDOFx1Njc4NFx1NUVGQVx1NzY4NFx1NkQ0Rlx1ODlDOFx1NTY2OFx1NTE3Q1x1NUJCOVx1NzZFRVx1NjgwN1xuICAgICAqL1xuICAgIHRhcmdldDogJ2VzMjAyMCcsXG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIHN0cmluZ1xuICAgICAqIFx1OUVEOFx1OEJBNDogZGlzdFxuICAgICAqIFx1NjMwN1x1NUI5QVx1OEY5M1x1NTFGQVx1OERFRlx1NUY4NFx1RkYwOFx1NzZGOFx1NUJGOVx1NEU4RSBcdTk4NzlcdTc2RUVcdTY4MzlcdTc2RUVcdTVGNTVcdUZGMDlcbiAgICAgKi9cbiAgICBvdXREaXI6ICdkaXN0JyxcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgc3RyaW5nXG4gICAgICogXHU5RUQ4XHU4QkE0OiBhc3NldHNcbiAgICAgKiBcdTYzMDdcdTVCOUFcdTc1MUZcdTYyMTBcdTk3NTlcdTYwMDFcdThENDRcdTZFOTBcdUZGMDhqc1x1MzAwMWNzc1x1MzAwMWltZ1x1MzAwMWZvbnRzXHVGRjA5XHU3Njg0XHU1QjU4XHU2NTNFXHU4REVGXHU1Rjg0XHVGRjA4XHU3NkY4XHU1QkY5XHU0RThFIGJ1aWxkLm91dERpclx1RkYwOVx1MzAwMlxuICAgICAqL1xuICAgIGFzc2V0c0RpcjogJ2Fzc2V0cycsXG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIG51bWJlclxuICAgICAqIFx1OUVEOFx1OEJBNFx1RkYxQSA0MDk2ICg0a2IpXG4gICAgICogXHU1QzBGXHU0RThFXHU2QjY0XHU5NjA4XHU1MDNDXHU3Njg0XHU1QkZDXHU1MTY1XHU2MjE2XHU1RjE1XHU3NTI4XHU4RDQ0XHU2RTkwXHU1QzA2XHU1MTg1XHU4MDU0XHU0RTNBIGJhc2U2NCBcdTdGMTZcdTc4MDFcdUZGMENcdTRFRTVcdTkwN0ZcdTUxNERcdTk4OURcdTU5MTZcdTc2ODQgaHR0cCBcdThCRjdcdTZDNDJcdTMwMDJcdThCQkVcdTdGNkVcdTRFM0EgMCBcdTUzRUZcdTRFRTVcdTVCOENcdTUxNjhcdTc5ODFcdTc1MjhcdTZCNjRcdTk4NzlcdTMwMDJcbiAgICAgKi9cbiAgICBhc3NldHNJbmxpbmVMaW1pdDogNDA5NixcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgYm9vbGVhblxuICAgICAqIFx1OUVEOFx1OEJBNDogdHJ1ZVxuICAgICAqIFx1NTQyRlx1NzUyOC9cdTc5ODFcdTc1MjggQ1NTIFx1NEVFM1x1NzgwMVx1NjJDNlx1NTIwNlx1MzAwMlx1NTk4Mlx1Njc5Q1x1Nzk4MVx1NzUyOFx1RkYwQ1x1NjU3NFx1NEUyQVx1OTg3OVx1NzZFRVx1NEUyRFx1NzY4NFx1NjI0MFx1NjcwOSBDU1MgXHU1QzA2XHU4OEFCXHU2M0QwXHU1M0Q2XHU1MjMwXHU0RTAwXHU0RTJBIENTUyBcdTY1ODdcdTRFRjZcdTRFMkRcbiAgICAgKi9cbiAgICBjc3NDb2RlU3BsaXQ6IHRydWUsXG4gICAgLyoqXG4gICAgICogXHU3QzdCXHU1NzhCXHVGRjFBIGJvb2xlYW4gfCAnaW5saW5lJyB8ICdoaWRkZW4nXG4gICAgICogXHU5RUQ4XHU4QkE0XHVGRjFBIGZhbHNlXG4gICAgICogXHU2Nzg0XHU1RUZBXHU1NDBFXHU2NjJGXHU1NDI2XHU3NTFGXHU2MjEwIHNvdXJjZSBtYXAgXHU2NTg3XHU0RUY2XHUzMDAyXHU1OTgyXHU2NzlDXHU0RTNBIHRydWVcdUZGMENcdTVDMDZcdTRGMUFcdTUyMUJcdTVFRkFcdTRFMDBcdTRFMkFcdTcyRUNcdTdBQ0JcdTc2ODQgc291cmNlIG1hcCBcdTY1ODdcdTRFRjZcdTMwMDJcbiAgICAgKiBcdTU5ODJcdTY3OUNcdTRFM0EgJ2lubGluZScgc291cmNlIG1hcCBcdTVDMDZcdTRGNUNcdTRFM0FcdTRFMDBcdTRFMkEgZGF0YSBVUkkgXHU5NjQ0XHU1MkEwXHU1NzI4XHU4RjkzXHU1MUZBXHU2NTg3XHU0RUY2XHU0RTJEXHUzMDAyXG4gICAgICogXHU1OTgyXHU2NzlDXHU0RTNBICdoaWRkZW4nIFx1NzY4NFx1NURFNVx1NEY1Q1x1NTM5Rlx1NzQwNlx1NEUwRSAndHJ1ZScgXHU3NkY4XHU0RjNDXHVGRjBDXHU1M0VBXHU2NjJGIGJ1bmRsZSBcdTY1ODdcdTRFRjZcdTRFMkRcdTc2RjhcdTVFOTRcdTc2ODRcdTZDRThcdTkxQ0FcdTVDMDZcdTRFMERcdTg4QUJcdTRGRERcdTc1NTlcdTMwMDJcbiAgICAgKi9cbiAgICBzb3VyY2VtYXA6IGZhbHNlLFxuICAgIC8qKlxuICAgICAqIFx1N0M3Qlx1NTc4Qlx1RkYxQSBSb2xsdXBPcHRpb25zXG4gICAgICogXHU4MUVBXHU1QjlBXHU0RTQ5XHU1RTk1XHU1QzQyXHU3Njg0IFJvbGx1cCBcdTYyNTNcdTUzMDVcdTkxNERcdTdGNkVcdTMwMDJcdThGRDlcdTRFMEVcdTRFQ0UgUm9sbHVwIFx1OTE0RFx1N0Y2RVx1NjU4N1x1NEVGNlx1NUJGQ1x1NTFGQVx1NzY4NFx1OTAwOVx1OTg3OVx1NzZGOFx1NTQwQ1x1RkYwQ1x1NUU3Nlx1NUMwNlx1NEUwRSBWaXRlIFx1NzY4NFx1NTE4NVx1OTBFOCBSb2xsdXAgXHU5MDA5XHU5ODc5XHU1NDA4XHU1RTc2XHUzMDAyXG4gICAgICovXG4gICAgcm9sbHVwT3B0aW9uczoge1xuICAgICAgLy8gXHU3ODZFXHU0RkREXHU1OTE2XHU5MEU4XHU1MzE2XHU1OTA0XHU3NDA2XHU5MEEzXHU0RTlCXHU0RjYwXHU0RTBEXHU2MEYzXHU2MjUzXHU1MzA1XHU4RkRCXHU1RTkzXHU3Njg0XHU0RjlEXHU4RDU2XG4gICAgICBleHRlcm5hbDogW10sXG4gICAgICAvLyBcdTYzMDdcdTVCOUFcdTY1ODdcdTRFRjZcdThGOTNcdTUxRkFcdTc2ODRcdTkxNERcdTdGNkVcbiAgICAgIG91dHB1dDoge1xuICAgICAgICBjaHVua0ZpbGVOYW1lczogJ2Fzc2V0cy9qcy9bbmFtZV0tW2hhc2hdLmpzJyxcbiAgICAgICAgZW50cnlGaWxlTmFtZXM6ICdhc3NldHMvanMvW25hbWVdLVtoYXNoXS5qcycsXG4gICAgICAgIGFzc2V0RmlsZU5hbWVzOiAnYXNzZXRzL1tleHRdL1tuYW1lXS1baGFzaF0uW2V4dF0nLFxuICAgICAgICBtYW51YWxDaHVua3MoaWQ6IGFueSkge1xuICAgICAgICAgIGlmIChpZC5pbmNsdWRlcygnbm9kZV9tb2R1bGVzJykpIHtcbiAgICAgICAgICAgIHJldHVybiAndmVuZG9yJyAvL1x1NEVFM1x1NzgwMVx1NTIwNlx1NTI3Mlx1NEUzQVx1N0IyQ1x1NEUwOVx1NjVCOVx1NTMwNVxuICAgICAgICAgIH1cbiAgICAgICAgfSxcbiAgICAgIH0sXG4gICAgfSxcbiAgICAvKnRlcnNlck9wdGlvbnM6IHtcbiAgICAgIC8vIFx1NjI1M1x1NTMwNVx1NTQwRVx1NzlGQlx1OTY2NGNvbnNvbGVcdTU0OENcdTZDRThcdTkxQ0FcbiAgICAgIGNvbXByZXNzOiB7XG4gICAgICAgIGtlZXBfaW5maW5pdHk6IHRydWUsXG4gICAgICAgIGRyb3BfY29uc29sZTogaXNCdWlsZCxcbiAgICAgICAgZHJvcF9kZWJ1Z2dlcjogaXNCdWlsZCxcbiAgICAgICAgcHVyZV9mdW5jczogWydjb25zb2xlLmxvZycsICdjb25zb2xlLmluZm8nXSxcbiAgICAgIH0sXG4gICAgfSwqL1xuICAgIC8qKlxuICAgICAqIFx1N0M3Qlx1NTc4Qlx1RkYxQSBib29sZWFuXG4gICAgICogXHU5RUQ4XHU4QkE0XHVGRjFBIHRydWVcbiAgICAgKiBcdTU0MkZcdTc1MjgvXHU3OTgxXHU3NTI4IGJyb3RsaSBcdTUzOEJcdTdGMjlcdTU5MjdcdTVDMEZcdTYyQTVcdTU0NEFcdTMwMDJcdTUzOEJcdTdGMjlcdTU5MjdcdTU3OEJcdThGOTNcdTUxRkFcdTY1ODdcdTRFRjZcdTUzRUZcdTgwRkRcdTRGMUFcdTVGODhcdTYxNjJcdUZGMENcdTU2RTBcdTZCNjRcdTc5ODFcdTc1MjhcdThCRTVcdTUyOUZcdTgwRkRcdTUzRUZcdTgwRkRcdTRGMUFcdTYzRDBcdTlBRDhcdTU5MjdcdTU3OEJcdTk4NzlcdTc2RUVcdTc2ODRcdTY3ODRcdTVFRkFcdTYwMjdcdTgwRkRcdTMwMDJcbiAgICAgKi9cbiAgICBicm90bGlTaXplOiBmYWxzZSxcbiAgICAvKipcbiAgICAgKiBcdTdDN0JcdTU3OEJcdUZGMUEgbnVtYmVyXG4gICAgICogXHU5RUQ4XHU4QkE0OiA1MDBcbiAgICAgKiBjaHVuayBcdTU5MjdcdTVDMEZcdThCNjZcdTU0NEFcdTc2ODRcdTk2NTBcdTUyMzZcdUZGMDhcdTRFRTUga2JzIFx1NEUzQVx1NTM1NVx1NEY0RFx1RkYwOVx1MzAwMlxuICAgICAqL1xuICAgIGNodW5rU2l6ZVdhcm5pbmdMaW1pdDogMjAwMCxcbiAgfVxufVxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcaW5kZXgudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L1RlbmdNaW5nL01hc3Rlci9DeWJlckZsdXgtRnJhbWV3b3JrL2N5YmVyZmx1eC1jbG91ZC11aS9jeWJlcmZsdXgtY2xvdWQtdnVlL2NvbmZpZy9wbHVnaW5zL2luZGV4LnRzXCI7aW1wb3J0IHZ1ZVBsdWdpbiBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUnXG5pbXBvcnQgdnVlSnN4UGx1Z2luIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZS1qc3gnXG5pbXBvcnQgbGVnYWN5UGx1Z2luIGZyb20gJ0B2aXRlanMvcGx1Z2luLWxlZ2FjeSdcbmltcG9ydCBzZXR1cEV4dGVuZFBsdWdpbiBmcm9tICd2aXRlLXBsdWdpbi12dWUtc2V0dXAtZXh0ZW5kJ1xuXG4vL2ltcG9ydCBwYWNrYWdlQ29uZmlnUGx1Z2luIGZyb20gJ3ZpdGUtcGx1Z2luLXBhY2thZ2UtY29uZmlnJ1xuLy9pbXBvcnQgb3B0aW1pemVQZXJzaXN0UGx1Z2luIGZyb20gJ3ZpdGUtcGx1Z2luLW9wdGltaXplLXBlcnNpc3QnXG5cbmltcG9ydCB7IGNvbmZpZ0F1dG9JbXBvcnRQbHVnaW4gfSBmcm9tICcuL2F1dG8taW1wb3J0J1xuaW1wb3J0IHsgY29uZmlnQ29tcG9uZW50c1BsdWdpbiB9IGZyb20gJy4vY29tcG9uZW50cydcbmltcG9ydCB7IGNvbmZpZ0NvbXByZXNzaW9uUGx1Z2luIH0gZnJvbSAnLi9jb21wcmVzc2lvbidcbmltcG9ydCB7IGNvbmZpZ0ltYWdlbWluUGx1Z2luIH0gZnJvbSAnLi9pbWFnZW1pbidcbmltcG9ydCB7IGNvbmZpZ0h0bWxQbHVnaW4gfSBmcm9tICcuL2h0bWwnXG5pbXBvcnQgeyBjb25maWdTdHlsZUltcG9ydFBsdWdpbiB9IGZyb20gJy4vc3R5bGUtaW1wb3J0J1xuXG5leHBvcnQgZnVuY3Rpb24gY3JlYXRlVml0ZVBsdWdpbnMoaXNCdWlsZDogYm9vbGVhbikgIHtcbiAgY29uc3QgcGx1Z2luczogYW55W10gPSBbXG4gICAgdnVlUGx1Z2luKCksXG4gICAgdnVlSnN4UGx1Z2luKCksXG4gICAgc2V0dXBFeHRlbmRQbHVnaW4oKSxcbiAgICBsZWdhY3lQbHVnaW4oe1xuICAgICAgdGFyZ2V0czogWydkZWZhdWx0cycsICdub3QgSUUgMTEnXSxcbiAgICAgIGFkZGl0aW9uYWxMZWdhY3lQb2x5ZmlsbHM6IFsncmVnZW5lcmF0b3ItcnVudGltZS9ydW50aW1lJ10sXG4gICAgfSksXG4gICAgLy9wYWNrYWdlQ29uZmlnUGx1Z2luLFxuICAgIC8vb3B0aW1pemVQZXJzaXN0UGx1Z2luLFxuICBdXG5cblxuICAvLyB1bnBsdWdpbi1hdXRvLWltcG9ydFxuICBwbHVnaW5zLnB1c2goY29uZmlnQXV0b0ltcG9ydFBsdWdpbigpKVxuICAvLyB1bnBsdWdpbi12dWUtY29tcG9uZW50c1xuICBwbHVnaW5zLnB1c2goY29uZmlnQ29tcG9uZW50c1BsdWdpbigpKVxuICAvLyB2b3RlLXBsdWdpbi1odG1sXG4gIHBsdWdpbnMucHVzaChjb25maWdIdG1sUGx1Z2luKGlzQnVpbGQpKVxuICAvLyB2aXRlLXBsdWdpbi1zdHlsZS1pbXBvcnRcbiAgcGx1Z2lucy5wdXNoKGNvbmZpZ1N0eWxlSW1wb3J0UGx1Z2luKCkpXG4gIGlmKGlzQnVpbGQpIHtcbiAgICAvLyB2aXRlLXBsdWdpbi1jb21wcmVzc2lvblxuICAgIHBsdWdpbnMucHVzaChjb25maWdDb21wcmVzc2lvblBsdWdpbigpKVxuICAgIC8vIHZpdGUtcGx1Z2luLWltYWdlbWluXG4gICAgcGx1Z2lucy5wdXNoKGNvbmZpZ0ltYWdlbWluUGx1Z2luKCkpXG4gIH1cbiAgcmV0dXJuIHBsdWdpbnNcbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXFxcXGF1dG8taW1wb3J0LnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9UZW5nTWluZy9NYXN0ZXIvQ3liZXJGbHV4LUZyYW1ld29yay9jeWJlcmZsdXgtY2xvdWQtdWkvY3liZXJmbHV4LWNsb3VkLXZ1ZS9jb25maWcvcGx1Z2lucy9hdXRvLWltcG9ydC50c1wiO2ltcG9ydCBhdXRvSW1wb3J0UGx1Z2luIGZyb20gJ3VucGx1Z2luLWF1dG8taW1wb3J0L3ZpdGUnXG5cbmV4cG9ydCBmdW5jdGlvbiBjb25maWdBdXRvSW1wb3J0UGx1Z2luKCkge1xuICByZXR1cm4gYXV0b0ltcG9ydFBsdWdpbih7XG4gICAgaW1wb3J0czogW1xuICAgICAgJ3Z1ZScsXG4gICAgXSxcbiAgICBkdHM6ICdjb25maWcvdHlwZXMvYXV0by1pbXBvcnRzLmQudHMnLFxuICB9KVxufVxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcY29tcG9uZW50cy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvY29uZmlnL3BsdWdpbnMvY29tcG9uZW50cy50c1wiO2ltcG9ydCBjb21wb25lbnRzUGx1Z2luIGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3ZpdGUnXG5pbXBvcnQgeyBOYWl2ZVVpUmVzb2x2ZXIgfSBmcm9tICd1bnBsdWdpbi12dWUtY29tcG9uZW50cy9yZXNvbHZlcnMnXG5cbmV4cG9ydCBmdW5jdGlvbiBjb25maWdDb21wb25lbnRzUGx1Z2luKCkge1xuICByZXR1cm4gY29tcG9uZW50c1BsdWdpbih7XG4gICAgZGlyczogWydzcmMvY29tcG9uZW50cyddLFxuICAgIHJlc29sdmVyczogW1xuICAgICAgTmFpdmVVaVJlc29sdmVyKClcbiAgICBdLFxuICAgIGR0czogJ2NvbmZpZy90eXBlcy9jb21wb25lbnRzLmQudHMnLFxuICAgIGV4dGVuc2lvbnM6IFsndnVlJ10sICAvLyBcdTdFQzRcdTRFRjZcdTc2ODRcdTY3MDlcdTY1NDhcdTY1ODdcdTRFRjZcdTYyNjlcdTVDNTVcdTU0MERcdTMwMDJcbiAgICBkaXJlY3RvcnlBc05hbWVzcGFjZTogZmFsc2UsIC8vIFx1NTE0MVx1OEJCOFx1NUI1MFx1NzZFRVx1NUY1NVx1NEY1Q1x1NEUzQVx1N0VDNFx1NEVGNlx1NzY4NFx1NTQ3RFx1NTQwRFx1N0E3QVx1OTVGNFx1NTI0RFx1N0YwMFx1MzAwMlxuICAgIGRlZXA6IHRydWUsXG4gIH0pXG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1xcXFxjb21wcmVzc2lvbi50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvY29uZmlnL3BsdWdpbnMvY29tcHJlc3Npb24udHNcIjtpbXBvcnQgY29tcHJlc3Npb25QbHVnaW4gZnJvbSAndml0ZS1wbHVnaW4tY29tcHJlc3Npb24nXG5cbmV4cG9ydCBmdW5jdGlvbiBjb25maWdDb21wcmVzc2lvblBsdWdpbihkZWxldGVPcmlnaW5GaWxlIDogYm9vbGVhbiA9IGZhbHNlKSB7XG4gIHJldHVybiBjb21wcmVzc2lvblBsdWdpbih7XG4gICAgZGlzYWJsZTogZmFsc2UsXG4gICAgdmVyYm9zZTogZmFsc2UsXG4gICAgdGhyZXNob2xkOiAxMDI0MCxcbiAgICBhbGdvcml0aG06ICdnemlwJyxcbiAgICBleHQ6ICcuZ3onLFxuICAgIGRlbGV0ZU9yaWdpbkZpbGUsXG4gIH0pXG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1xcXFxpbWFnZW1pbi50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvY29uZmlnL3BsdWdpbnMvaW1hZ2VtaW4udHNcIjtpbXBvcnQgaW1hZ2VtaW5QbHVnaW4gZnJvbSAndml0ZS1wbHVnaW4taW1hZ2VtaW4nXHJcblxyXG5leHBvcnQgZnVuY3Rpb24gY29uZmlnSW1hZ2VtaW5QbHVnaW4oKSB7XHJcbiAgcmV0dXJuIGltYWdlbWluUGx1Z2luKHtcclxuICAgIGdpZnNpY2xlOiB7XHJcbiAgICAgIG9wdGltaXphdGlvbkxldmVsOiA3LFxyXG4gICAgICBpbnRlcmxhY2VkOiBmYWxzZSxcclxuICAgIH0sXHJcbiAgICBvcHRpcG5nOiB7XHJcbiAgICAgIG9wdGltaXphdGlvbkxldmVsOiA3LFxyXG4gICAgfSxcclxuICAgIG1vempwZWc6IHtcclxuICAgICAgcXVhbGl0eTogMjAsXHJcbiAgICB9LFxyXG4gICAgcG5ncXVhbnQ6IHtcclxuICAgICAgcXVhbGl0eTogWzAuOCwgMC45XSxcclxuICAgICAgc3BlZWQ6IDQsXHJcbiAgICB9LFxyXG4gICAgc3Znbzoge1xyXG4gICAgICBwbHVnaW5zOiBbXHJcbiAgICAgICAge1xyXG4gICAgICAgICAgbmFtZTogJ3JlbW92ZVZpZXdCb3gnLFxyXG4gICAgICAgIH0sXHJcbiAgICAgICAge1xyXG4gICAgICAgICAgbmFtZTogJ3JlbW92ZUVtcHR5QXR0cnMnLFxyXG4gICAgICAgICAgYWN0aXZlOiBmYWxzZSxcclxuICAgICAgICB9LFxyXG4gICAgICBdLFxyXG4gICAgfSxcclxuICB9KVxyXG59IiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFxjb25maWdcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcXFxcaHRtbC50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvY29uZmlnL3BsdWdpbnMvaHRtbC50c1wiOy8qKlxuICogUGx1Z2luIHRvIG1pbmltaXplIGFuZCB1c2UgZWpzIHRlbXBsYXRlIHN5bnRheCBpbiBpbmRleC5odG1sLlxuICogaHR0cHM6Ly9naXRodWIuY29tL2FubmN3Yi92aXRlLXBsdWdpbi1odG1sXG4gKi9cbmltcG9ydCB7IGNyZWF0ZUh0bWxQbHVnaW4gfSBmcm9tICd2aXRlLXBsdWdpbi1odG1sJ1xuaW1wb3J0IHBrZyBmcm9tICcuLi8uLi9wYWNrYWdlLmpzb24nXG5cbmNvbnN0IEdMT0JfQ09ORklHX0ZJTEVfTkFNRSA9ICdhcHAuY29uZmlnLmpzJ1xuY29uc3QgVklURV9BUFBfVElUTEUgPSAnQ3liZXJGbHV4IENsb3VkJ1xuY29uc3QgVklURV9QVUJMSUNfUEFUSCA9Jy8nXG5cbmV4cG9ydCBmdW5jdGlvbiBjb25maWdIdG1sUGx1Z2luKGlzQnVpbGQ6IGJvb2xlYW4pIHtcblxuICBjb25zdCBwYXRoID0gVklURV9QVUJMSUNfUEFUSC5lbmRzV2l0aCgnLycpID8gVklURV9QVUJMSUNfUEFUSCA6IGAke1ZJVEVfUFVCTElDX1BBVEh9L2A7XG5cbiAgY29uc3QgZ2V0QXBwQ29uZmlnU3JjID0gKCkgPT4ge1xuICAgIHJldHVybiBgJHtwYXRoIHx8ICcvJ30ke0dMT0JfQ09ORklHX0ZJTEVfTkFNRX0/dj0ke3BrZy52ZXJzaW9ufS0ke25ldyBEYXRlKCkuZ2V0VGltZSgpfWA7XG4gIH07XG5cbiAgY29uc3QgaHRtbFBsdWdpbiA9IGNyZWF0ZUh0bWxQbHVnaW4oe1xuICAgIG1pbmlmeTogaXNCdWlsZCxcbiAgICBpbmplY3Q6IHtcbiAgICAgIC8vIEluamVjdCBkYXRhIGludG8gZWpzIHRlbXBsYXRlXG4gICAgICBkYXRhOiB7XG4gICAgICAgIHRpdGxlOiBWSVRFX0FQUF9USVRMRSxcbiAgICAgIH0sXG4gICAgICAvLyBFbWJlZCB0aGUgZ2VuZXJhdGVkIGFwcC5jb25maWcuanMgZmlsZVxuICAgICAgdGFnczogaXNCdWlsZCA/IFt7XG4gICAgICAgIHRhZzogJ3NjcmlwdCcsXG4gICAgICAgIGF0dHJzOiB7XG4gICAgICAgICAgc3JjOiBnZXRBcHBDb25maWdTcmMoKSxcbiAgICAgICAgfVxuICAgICAgfV0gOiBbXSxcbiAgICB9LFxuICB9KTtcbiAgcmV0dXJuIGh0bWxQbHVnaW47XG59XG4iLCAie1xuICBcIm5hbWVcIjogXCJjeWJlcmZsdXgtY2xvdWRcIixcbiAgXCJwcml2YXRlXCI6IHRydWUsXG4gIFwidmVyc2lvblwiOiBcIkFscGhhXCIsXG4gIFwidHlwZVwiOiBcIm1vZHVsZVwiLFxuICBcImF1dGhvclwiOiB7XG4gICAgXCJuYW1lXCI6IFwiTWluZ1wiLFxuICAgIFwiZW1haWxcIjogXCJkbTU4NjIxMTBAb3V0bG9vay5jb21cIlxuICB9LFxuICBcInJlc29sdXRpb25zXCI6IHtcbiAgICBcImJpbi13cmFwcGVyXCI6IFwibnBtOmJpbi13cmFwcGVyLWNoaW5hXCJcbiAgfSxcbiAgXCJzY3JpcHRzXCI6IHtcbiAgICBcImJvb3RzdHJhcFwiOiBcInBucG0gaW5zdGFsbFwiLFxuICAgIFwiYnVpbGRcIjogXCJ2dWUtdHNjICYmIHZpdGUgYnVpbGRcIixcbiAgICBcImRldlwiOiBcInZpdGVcIixcbiAgICBcInByZXZpZXdcIjogXCJ2aXRlIHByZXZpZXdcIixcbiAgICBcImxpbnQ6ZXNsaW50XCI6IFwiZXNsaW50IFxcXCJ7c3JjfS8qKi8qLnt2dWUsdHMsdHN4fVxcXCIgLS1maXhcIixcbiAgICBcImxpbnQ6cHJldHRpZXJcIjogXCJwcmV0dGllciAtLXdyaXRlIC0tbG9nbGV2ZWwgd2FybiBcXFwic3JjLyoqLyoue2pzLGpzb24sdHN4LGNzcyxsZXNzLHNjc3MsdnVlLGh0bWwsbWR9XFxcIlwiLFxuICAgIFwibGludDpzdHlsZWxpbnRcIjogXCJzdHlsZWxpbnQgLS1maXggXFxcIioqLyoue3Z1ZSxsZXNzLHBvc3Rjc3MsY3NzLHNjc3N9XFxcIiAtLWNhY2hlIC0tY2FjaGUtbG9jYXRpb24gbm9kZV9tb2R1bGVzLy5jYWNoZS9zdHlsZWxpbnQvXCJcbiAgfSxcbiAgXCJkZXBlbmRlbmNpZXNcIjoge1xuICAgIFwiQHZpY29ucy9hbnRkXCI6IFwiXjAuMTIuMFwiLFxuICAgIFwiQHZpY29ucy9pb25pY29uczVcIjogXCJeMC4xMi4wXCIsXG4gICAgXCJAdnVldXNlL2NvcmVcIjogXCJeMTAuNC4xXCIsXG4gICAgXCJheGlvc1wiOiBcIl4xLjIuMlwiLFxuICAgIFwiYnVmZmVyXCI6IFwiXjYuMC4zXCIsXG4gICAgXCJoaWdoY2hhcnRzXCI6IFwiXjEwLjMuM1wiLFxuICAgIFwiaGlnaGNoYXJ0cy12dWVcIjogXCJeMS40LjNcIixcbiAgICBcImxpbnQtc3RhZ2VkXCI6IFwiXjEzLjMuMFwiLFxuICAgIFwibG9kYXNoLWVzXCI6IFwiXjQuMTcuMjFcIixcbiAgICBcIm1xdHRcIjogXCJeNC4zLjdcIixcbiAgICBcIm5haXZlLXVpXCI6IFwiXjIuMzQuNFwiLFxuICAgIFwicGluaWFcIjogXCJeMi4xLjZcIixcbiAgICBcInFzXCI6IFwiXjYuMTEuMlwiLFxuICAgIFwidmZvbnRzXCI6IFwiXjAuMC4zXCIsXG4gICAgXCJ2dWVcIjogXCJeMy4zLjRcIixcbiAgICBcInZ1ZS1pMThuXCI6IFwiXjkuMi4yXCIsXG4gICAgXCJ2dWUtcm91dGVyXCI6IFwiXjQuMi40XCIsXG4gICAgXCJ2dWUtdGltZWFnbzNcIjogXCJeMi4zLjFcIixcbiAgICBcInZ1ZS10eXBlc1wiOiBcIl41LjEuMVwiLFxuICAgIFwidnVlMy1pY29uLXBpY2tlclwiOiBcIl4wLjEuMVwiLFxuICAgIFwidnVlZHJhZ2dhYmxlXCI6IFwiXjQuMS4wXCJcbiAgfSxcbiAgXCJkZXZEZXBlbmRlbmNpZXNcIjoge1xuICAgIFwiQGludGxpZnkvdml0ZS1wbHVnaW4tdnVlLWkxOG5cIjogXCJeNy4wLjBcIixcbiAgICBcIkB0eXBlcy9sb2Rhc2gtZXNcIjogXCJeNC4xNy45XCIsXG4gICAgXCJAdHlwZXMvdGhyZWVcIjogXCJeMC4xNTIuMVwiLFxuICAgIFwiQHR5cGVzY3JpcHQtZXNsaW50L2VzbGludC1wbHVnaW5cIjogXCJeNS42Mi4wXCIsXG4gICAgXCJAdHlwZXNjcmlwdC1lc2xpbnQvcGFyc2VyXCI6IFwiXjUuNjIuMFwiLFxuICAgIFwiQHZpdGVqcy9wbHVnaW4tbGVnYWN5XCI6IFwiXjQuMS4xXCIsXG4gICAgXCJAdml0ZWpzL3BsdWdpbi12dWVcIjogXCJeNC4zLjRcIixcbiAgICBcIkB2aXRlanMvcGx1Z2luLXZ1ZS1qc3hcIjogXCJeMy4wLjJcIixcbiAgICBcImF1dG9wcmVmaXhlclwiOiBcIl4xMC40LjE1XCIsXG4gICAgXCJjb25zb2xhXCI6IFwiXjMuMi4zXCIsXG4gICAgXCJjb3JlLWpzXCI6IFwiXjMuMzIuMVwiLFxuICAgIFwiZXNsaW50XCI6IFwiXjguNDguMFwiLFxuICAgIFwiZXNsaW50LWRlZmluZS1jb25maWdcIjogXCJeMS4yMy4wXCIsXG4gICAgXCJlc2xpbnQtcGx1Z2luLXZ1ZVwiOiBcIl45LjE3LjBcIixcbiAgICBcInBvc3Rjc3NcIjogXCJeOC40LjI5XCIsXG4gICAgXCJwb3N0Y3NzLWh0bWxcIjogXCJeMS41LjBcIixcbiAgICBcInBvc3Rjc3Mtc2Nzc1wiOiBcIl40LjAuN1wiLFxuICAgIFwicmVzaXplLW9ic2VydmVyLXBvbHlmaWxsXCI6IFwiXjEuNS4xXCIsXG4gICAgXCJzYXNzXCI6IFwiXjEuNjYuMVwiLFxuICAgIFwic2Fzcy1sb2FkZXJcIjogXCJeMTMuMy4yXCIsXG4gICAgXCJzdHlsZWxpbnRcIjogXCJeMTUuMTAuM1wiLFxuICAgIFwic3R5bGVsaW50LWNvbmZpZy1wcmV0dGllclwiOiBcIl45LjAuNVwiLFxuICAgIFwic3R5bGVsaW50LWNvbmZpZy1yZWNvbW1lbmRlZC1zY3NzXCI6IFwiXjEwLjAuMFwiLFxuICAgIFwic3R5bGVsaW50LWNvbmZpZy1zdGFuZGFyZFwiOiBcIl4zMi4wLjBcIixcbiAgICBcInN0eWxlbGludC1jb25maWctc3RhbmRhcmQtdnVlXCI6IFwiXjEuMC4wXCIsXG4gICAgXCJzdHlsZWxpbnQtb3JkZXJcIjogXCJeNi4wLjNcIixcbiAgICBcInN0eWxlbGludC1zY3NzXCI6IFwiXjQuNy4wXCIsXG4gICAgXCJ0YWlsd2luZGNzc1wiOiBcIl4zLjMuM1wiLFxuICAgIFwidHlwZXNjcmlwdFwiOiBcIl40LjkuNVwiLFxuICAgIFwidW5wbHVnaW4tYXV0by1pbXBvcnRcIjogXCJeMC4xNS4zXCIsXG4gICAgXCJ1bnBsdWdpbi12dWUtY29tcG9uZW50c1wiOiBcIl4wLjI0LjFcIixcbiAgICBcInZpdGVcIjogXCJeNC40LjlcIixcbiAgICBcInZpdGUtcGx1Z2luLWNvbXByZXNzaW9uXCI6IFwiXjAuNS4xXCIsXG4gICAgXCJ2aXRlLXBsdWdpbi1odG1sXCI6IFwiXjMuMi4wXCIsXG4gICAgXCJ2aXRlLXBsdWdpbi1pbWFnZW1pblwiOiBcIl4wLjYuMVwiLFxuICAgIFwidml0ZS1wbHVnaW4tb3B0aW1pemUtcGVyc2lzdFwiOiBcIl4wLjEuMlwiLFxuICAgIFwidml0ZS1wbHVnaW4tcGFja2FnZS1jb25maWdcIjogXCJeMC4xLjFcIixcbiAgICBcInZpdGUtcGx1Z2luLXN0eWxlLWltcG9ydFwiOiBcIl4yLjAuMFwiLFxuICAgIFwidml0ZS1wbHVnaW4tdnVlLXNldHVwLWV4dGVuZFwiOiBcIl4wLjQuMFwiLFxuICAgIFwidnVlLWdsb2JhbC1hcGlcIjogXCJeMC40LjFcIixcbiAgICBcInZ1ZS10c2NcIjogXCJeMS44LjhcIlxuICB9LFxuICBcInZpdGVcIjoge1xuICAgIFwib3B0aW1pemVEZXBzXCI6IHtcbiAgICAgIFwiaW5jbHVkZVwiOiBbXG4gICAgICAgIFwidnVlXCIsXG4gICAgICAgIFwidnVlLWdsb2JhbC1hcGlcIlxuICAgICAgXVxuICAgIH1cbiAgfSxcbiAgXCJsaW50LXN0YWdlZFwiOiB7XG4gICAgXCIqLnt2dWUsanMsdHMsdHN4fVwiOiBcImVzbGludCAtLWZpeFwiXG4gIH1cbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHBsdWdpbnNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXFxcXGNvbmZpZ1xcXFxwbHVnaW5zXFxcXHN0eWxlLWltcG9ydC50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvY29uZmlnL3BsdWdpbnMvc3R5bGUtaW1wb3J0LnRzXCI7aW1wb3J0IHsgY3JlYXRlU3R5bGVJbXBvcnRQbHVnaW4gfSBmcm9tICd2aXRlLXBsdWdpbi1zdHlsZS1pbXBvcnQnXG5cbmV4cG9ydCBmdW5jdGlvbiBjb25maWdTdHlsZUltcG9ydFBsdWdpbigpIHtcbiAgcmV0dXJuIGNyZWF0ZVN0eWxlSW1wb3J0UGx1Z2luKHtcbiAgICByZXNvbHZlczogW11cbiAgfSlcbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRDpcXFxcVGVuZ01pbmdcXFxcTWFzdGVyXFxcXEN5YmVyRmx1eC1GcmFtZXdvcmtcXFxcY3liZXJmbHV4LWNsb3VkLXVpXFxcXGN5YmVyZmx1eC1jbG91ZC12dWVcXFxcY29uZmlnXFxcXHJlc29sdmVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXFxcXGNvbmZpZ1xcXFxyZXNvbHZlXFxcXGluZGV4LnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9UZW5nTWluZy9NYXN0ZXIvQ3liZXJGbHV4LUZyYW1ld29yay9jeWJlcmZsdXgtY2xvdWQtdWkvY3liZXJmbHV4LWNsb3VkLXZ1ZS9jb25maWcvcmVzb2x2ZS9pbmRleC50c1wiO2ltcG9ydCB7IHJlc29sdmUgfSBmcm9tICdwYXRoJ1xuXG5leHBvcnQgZnVuY3Rpb24gY3JlYXRlVml0ZVJlc29sdmUoKSAge1xuICByZXR1cm4ge1xuICAgIGFsaWFzOiB7XG4gICAgICAnfic6IHJlc29sdmUocHJvY2Vzcy5jd2QoKSwgJy4nKSxcbiAgICAgICdAJzogcmVzb2x2ZShwcm9jZXNzLmN3ZCgpLCAnc3JjJyksXG4gICAgICAnIyc6IHJlc29sdmUocHJvY2Vzcy5jd2QoKSwgJ3R5cGVzJyksXG4gICAgICAnQGxpYnJhcnknOiByZXNvbHZlKHByb2Nlc3MuY3dkKCksICdsaWJyYXJ5JyksXG4gICAgICAndnVlLWkxOG4nOiAndnVlLWkxOG4vZGlzdC92dWUtaTE4bi5janMuanMnLCAvLyBcdTg5RTNcdTUxQjN3cmFuXG4gICAgICAnbXF0dCc6ICdtcXR0L2Rpc3QvbXF0dC5qcycsXG4gICAgfSxcbiAgICAvLyBodHRwczovL2NuLnZpdGVqcy5kZXYvY29uZmlnLyNyZXNvbHZlLWV4dGVuc2lvbnNcbiAgICBleHRlbnNpb25zOiBbJy5tanMnLCAnLmpzJywgJy50cycsICcuanN4JywgJy50c3gnLCAnLmpzb24nLCAnLnZ1ZSddLFxuICB9XG59XG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkQ6XFxcXFRlbmdNaW5nXFxcXE1hc3RlclxcXFxDeWJlckZsdXgtRnJhbWV3b3JrXFxcXGN5YmVyZmx1eC1jbG91ZC11aVxcXFxjeWJlcmZsdXgtY2xvdWQtdnVlXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxUZW5nTWluZ1xcXFxNYXN0ZXJcXFxcQ3liZXJGbHV4LUZyYW1ld29ya1xcXFxjeWJlcmZsdXgtY2xvdWQtdWlcXFxcY3liZXJmbHV4LWNsb3VkLXZ1ZVxcXFx2aXRlLmNvbmZpZy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovVGVuZ01pbmcvTWFzdGVyL0N5YmVyRmx1eC1GcmFtZXdvcmsvY3liZXJmbHV4LWNsb3VkLXVpL2N5YmVyZmx1eC1jbG91ZC12dWUvdml0ZS5jb25maWcudHNcIjtpbXBvcnQgeyBVc2VyQ29uZmlnLCBDb25maWdFbnYgfSBmcm9tICd2aXRlJztcblxuaW1wb3J0IHtcbiAgY3JlYXRlVml0ZUJ1aWxkLCBjcmVhdGVWaXRlUGx1Z2lucywgY3JlYXRlVml0ZVJlc29sdmVcbn0gZnJvbSAnLi9jb25maWcnO1xuXG5leHBvcnQgZGVmYXVsdCAoeyBjb21tYW5kLCBtb2RlIH06IENvbmZpZ0Vudik6IFVzZXJDb25maWcgPT4ge1xuICBjb25zdCBpc0J1aWxkID0gKGNvbW1hbmQgPT09ICdidWlsZCcpXG4gIHJldHVybiB7XG4gICAgYmFzZTogJy4vJyxcbiAgICBidWlsZDogY3JlYXRlVml0ZUJ1aWxkKGlzQnVpbGQpLFxuICAgIHBsdWdpbnM6IGNyZWF0ZVZpdGVQbHVnaW5zKGlzQnVpbGQpLFxuICAgIHJlc29sdmU6IGNyZWF0ZVZpdGVSZXNvbHZlKCksXG4gICAgc2VydmVyOiB7XG4gICAgICBob3N0OiAnMC4wLjAuMCcsXG4gICAgICBwb3J0OiAxODA4MCxcbiAgICAgIGh0dHBzOiBmYWxzZSxcbiAgICAgIHByb3h5OiB7XG4gICAgICAgICcvYXBpJzoge1xuICAgICAgICAgIHRhcmdldDogJ2h0dHA6Ly9sb2NhbGhvc3Q6MTgwODcnLFxuICAgICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSxcbiAgICAgICAgICByZXdyaXRlOiBwYXRoID0+IHBhdGgucmVwbGFjZSgvXlxcL2FwaS8sICcnKSxcbiAgICAgICAgfVxuICAgICAgfVxuICAgIH0sXG4gICAgY3NzOiB7XG4gICAgICBwcmVwcm9jZXNzb3JPcHRpb25zOiB7XG4gICAgICAgIHNjc3M6IHtcbiAgICAgICAgICBqYXZhc2NyaXB0RW5hYmxlZDogdHJ1ZSxcbiAgICAgICAgICBhZGRpdGlvbmFsRGF0YTogJ0BpbXBvcnQgXCJAL2Fzc2V0cy9zdHlsZS92YXIuc2Nzc1wiOydcbiAgICAgICAgfVxuICAgICAgfVxuICAgIH0sXG4gICAgb3B0aW1pemVEZXBzOiB7XG5cbiAgICB9LFxuICB9XG59XG4iXSwKICAibWFwcGluZ3MiOiAiO0FBQTJjLFNBQVMsZ0JBQWdCLFNBQWtCO0FBQ3BmLFNBQU87QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsSUFNTCxRQUFRO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBTVIsUUFBUTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQSxJQU1SLFdBQVc7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsSUFNWCxtQkFBbUI7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsSUFNbkIsY0FBYztBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7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
