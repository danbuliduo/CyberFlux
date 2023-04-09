const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost',
    port: 18080,
  },
  chainWebpack: config => {
    config.resolve.extensions.add('ts').add('tsx');
  }
})