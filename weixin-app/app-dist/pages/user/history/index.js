import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'
const {fly, lodash} = getApp()
const { findIndex } = lodash

Page({
  data: {
    limit: 20,
    page: 1,
    hasNextPage: true,
    historyList: [],
  },
  async onLoad() {
    const {limit, page} = this.data
    await this.getList({limit, page})
  },
  goMerchant({detail}) {
    wx.navigateTo({
      url: `/pages/home/merchant/index?id=${detail.id}`
    })
  },
  async getList({limit, page}) {
    try {
      const res = await fly.post('/customer/footprint/getFootprintList', {
        limit,
        page,
      })
      wx.stopPullDownRefresh()
      const loadList =  res.data.footprintList
      this.setData({
        historyList: page === 1 ? loadList : this.data.footprintList.concat(loadList),
        hasNextPage: res.data.footprintList.length === this.data.limit,
        page: res.data.footprintList.length === this.data.limit ? this.data.page + 1 : this.data.page,
      })
    }catch (e) {
      wx.stopPullDownRefresh()
    }
  },
  onPullDownRefresh() {
    this.getList({limit: 20, page: 1,})
  },
  onReachBottom() {
    this.getList()
  },
})
