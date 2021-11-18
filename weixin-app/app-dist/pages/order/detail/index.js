const {fly}=getApp()
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'

Page({

  /**
   * 页面的初始数据
   */
  data: {
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.fetchOrderDetail(options.id)
  },
  async fetchOrderDetail(orderId) {
    const response = await fly.post('/order/getOrderDetails', {orderId})
    this.setData({
      orderDetail: response.data.order,
      products: response.data.order.orderGoodsList
    })
  },
})
