const {get} = getApp().lodash

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    products: {
      type: Array,
    },
    prices: {
      type: Object,
    },
    title: {
      type: String,
    },
  },
  options: {
    addGlobalClass: true
  },
  /**
   * 组件的初始数据
   */
  data: {
    get,
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
