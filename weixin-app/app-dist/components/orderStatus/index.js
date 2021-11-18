// components/orderStatus/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    status: {
      type: String|Number,
      value: 0,
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    statusData: {
      0: '待付款',
      101: '取货中',
      103: '洗涤中',
      104: '送货中',
      2: '已完成',
    },
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
