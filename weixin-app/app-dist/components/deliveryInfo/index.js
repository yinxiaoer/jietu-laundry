// components/orderInfo/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    orderDetail: {
      type: Object,
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    makePhone() {
      wx.makePhoneCall({
        phoneNumber: '1008611' // 仅为示例，并非真实的电话号码
      })
    },
  }
})
