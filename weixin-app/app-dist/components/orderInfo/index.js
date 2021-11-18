// components/orderInfo/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    orderDetail: {
      type: Object,
      observer(value) {
        const moment = require('../../utils/comment');
        const createDate = moment(new Date(value.createDate))
        this.setData({
          createDate: createDate.format('YYYY/MM/d')
      })
      },
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

  }
})
