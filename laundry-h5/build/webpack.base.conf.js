var path = require('path')
var utils = require('./utils')
var config = require('../config')
var vueLoaderConfig = require('./vue-loader.conf')
const vuxLoader = require('vux-loader')
function resolve(dir) {
  return path.join(__dirname, '..', dir)
}

const getPublicPath = ()=> {
  if(process.env.NODE_ENV === 'production') {
    return config.build.assetsPublicPath
  } else {
    return config.dev.assetsPublicPath
  }
}

const webpackConfig = {
  entry: {
    app: './src/main.js'
  },
  output: {
    path: config.build.assetsRoot,
    filename: '[name].js',
    publicPath: getPublicPath()
  },
  resolve: {
    extensions: ['.js', '.vue', '.json','.less'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      '@': resolve('src'),
      'src': path.resolve(__dirname, '../src'),
      'assets': path.resolve(__dirname, '../src/assets'),
      'components': path.resolve(__dirname, '../src/components'),
      'views': path.resolve(__dirname, '../src/views'),
      'styles': path.resolve(__dirname, '../src/styles'),
      'api': path.resolve(__dirname, '../src/api'),
      'utils': path.resolve(__dirname, '../src/utils'),
      'store': path.resolve(__dirname, '../src/store'),
      'router': path.resolve(__dirname, '../src/router'),
      'mock': path.resolve(__dirname, '../src/mock'),
      'vendor': path.resolve(__dirname, '../src/vendor'),
      'static': path.resolve(__dirname, '../static')
    }
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        loader: 'babel-loader?cacheDirectory',
        include: [resolve('src'), resolve('test')]
      },
      {
        test: /\.svg$/,
        loader: 'svg-sprite-loader',
        include: [resolve('src/icons')],
        options: {
          symbolId: 'icon-[name]'
        }
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        exclude: [resolve('src/icons')],
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      }
    ]
  },
  //????????????mixin
  // sassResources: path.join(__dirname, '../src/styles/mixin.scss'),
  // sassLoader: {
  //     data:  path.join(__dirname, '../src/styles/index.scss')
  // },
}
module.exports = vuxLoader.merge(webpackConfig, { plugins: ['vux-ui'] });

