const {fly} = getApp()
import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentTab: '',
    orders: [],
    orderStatus: {
      0: '待付款',
      1: '订单已完成',
      101: '待取货',
      102: '配送到商家中',
      103: '洗涤中',
      104: '等待配送到用户',
      105: '配送到用户中',
      2: '待评价',
      201: '退款中',
      202: '退款完成',
      3: '订单已取消',
    },
    page: 1,
    limit: 10,
    hasNextPage: true,
  },
  onShow() {
    this.setData({
      hasNextPage: true,
    })
    this.getOrders(this.data)
  },
  async getOrders({ page, limit , hasNextPage, currentTab }) {
    if(!hasNextPage) {
      return false
    }
    const query = Object.assign({page, limit},
        currentTab && {
          statusList: currentTab.split(','),
        })
    try {
      const response = await fly.post('/order/getCustomerOrderList', query)
      wx.stopPullDownRefresh()
      this.setData({
        orders: page == 1 ? response.data.orderList : this.data.orders.concat(response.data.orderList),
        page: page,
        hasNextPage: !(response.data.orderList.length<limit)
      })
    }catch (e) {
      throw e;
    }

  },
  onChange({detail}) {
    this.setData({
      currentTab: detail.key,
      hasNextPage: true,
      page: 1,
    })
    this.getOrders(this.data)
  },
  onPullDownRefresh() {
    const pagination = {
      ...this.data,
      page: 1,
      hasNextPage: true,
    }
    this.getOrders(pagination)
  },
  onReachBottom() {
    const pagination = {
      ...this.data,
      page: this.data.page + 1,
    }
    this.getOrders(pagination)
  },
})
