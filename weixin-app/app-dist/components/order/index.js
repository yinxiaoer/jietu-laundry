import comment from '../../utils/comment'
import {ORDER_STATUS} from '../../constant/ORDER_STATUS'

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    item: {
      type: Object,
      observer() {
        const item = this.data.item
        this.setData({
          orderItem: {
            ...item,
            statusName: ORDER_STATUS[item.status],
            createDate: comment(item.createDate).format('YYYY-MM-DD')
          },
        })
      }
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    orderItem: {},
    ORDER_STATUS,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    goDetail() {
      wx.navigateTo({
        url: '/pages/order/detail/index?id='+this.data.orderItem.id
      })
    },
    comment() {
      wx.navigateTo({
        url: '/pages/order/comment/index?id='+this.data.orderItem.id
      })
    },
    goMerchant() {
      wx.navigateTo({
        url: '/pages/home/merchant/index?id='+this.data.orderItem.merchantId
      })
    },
    goPay() {
      wx.navigateTo({
        url: `/pages/payment/index?orderId=${this.data.item.id}&payAmount=${this.data.item.payAmount}`,
      })
    },
  }
})
