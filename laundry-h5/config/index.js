// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path');

module.exports = {
    build: {
			prodEnv: require('./prod.env'),
      testEnv: require('./test.env'),
			index: path.resolve(__dirname, '../dist/index.html'),
			assetsRoot: path.resolve(__dirname, '../dist'),
			assetsSubDirectory: 'static',
			assetsPublicPath: './',          //请根据自己路径配置更改
			productionSourceMap: true,
			productionGzip: false,
			productionGzipExtensions: ['js', 'css'],
			bundleAnalyzerReport: process.env.npm_config_report
    },
    dev: {
			env: require('./dev.env'),
			port: 9527,
			autoOpenBrowser: false,
			assetsSubDirectory: 'static',
			assetsPublicPath: '/',
			proxyTable: {
				'/admin': {
					target:'http://localhost:9802/admin',//开发环境
					changeOrigin:true,
					secure:false,
					pathRewrite: {"^/admin" : ""}
				}
			},
			cssSourceMap: false
    },
}
