const { defineConfig } = require('@vue/cli-service')
require("babel-polyfill")
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    client: {
      overlay: false,
    },
    allowedHosts: [
      'lot.com'
    ],
    open: false, // 编译完成是否打开网页
    host: 'localhost', // 指定使用地址，默认localhost,0.0.0.0代表可以被外界访问
    port: 80,
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:9999',
        changeOrigin: true
      }
    }
  }
})


