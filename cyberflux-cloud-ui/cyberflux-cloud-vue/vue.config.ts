import { defineConfig } from '@vue/cli-service';

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    hot: true,
    host: 'localhost',
    port: 18080,
  },
  chainWebpack: config => {
    config.resolve.extensions.add('ts').add('tsx');
  },
  publicPath: process.env.VUE_APP_PUBLIC_PATH,
  outputDir: 'dist',
  assetsDir: 'assets',
})