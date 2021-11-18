// components/appraisesDetail/index.js
const { isEmpty } = getApp().lodash
const moment = require('../../utils/comment');

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    appraisedetail: {
      type: Object,
      value: {},
      observer(value) {
        const createDate = moment(new Date(value.createDate.replace(/\-/g, '/')))
        this.setData({
          username_type: isEmpty(value.customerName),
          appraiseimg: value.imageList.length>0,
          visibleMerchantreply: !isEmpty(value.merchantReply.trim()),
          date: createDate.format('YYYY/MM/DD')
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
