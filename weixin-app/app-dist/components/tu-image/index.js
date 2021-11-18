// components/tu-image/index.js
const {config} = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    src: {
      type: String,
      value: ''
    },
    mode: {
      type: String,
      value: 'aspectFill'
    },
    lazyLoad: {
      type: Boolean,
      value: false
    },
    urlType: {
      type: String,
      value: 'relative' // absolute
    },
  },
  options: {
    addGlobalClass: true
  },
  /**
   * 组件的初始数据
   */
  data: {
    config: config,
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
