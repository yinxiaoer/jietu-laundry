import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'
const {fly, lodash} = getApp()
const { findIndex } = lodash

Page({
  data: {
    limit: 20,
    page: 1,
    hasNextPage: true,
    collectionList: [],
  },
  async onLoad() {
    const {limit, page} = this.data
    await this.getList({limit, page})
  },
  async getList({limit, page}) {
    try {
      const res = await fly.post('/customer/vipCard/getVipCardList', {
        limit,
        page,
      })
      wx.stopPullDownRefresh()
      const loadList =  res.data.vipCardList.map(item => ({
        ...item,
      }))

      this.setData({
        collectionList: page === 1 ? loadList : this.data.vipCardList.concat(loadList),
        hasNextPage: res.data.vipCardList.length === this.data.limit,
        page: res.data.vipCardList.length === this.data.limit ? this.data.page + 1 : this.data.page,
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
  goMerchant({detail}) {
    wx.navigateTo({
      url: `/pages/home/merchant/index?id=${detail.id}&origin=merchant`
    })
  },
  async cancelCollect({ detail }) {
    try {
      await fly.post(`/customer/collection/delete/${detail.id}`)
      wx.showToast({
        title: '取消收藏成功',
      })
      const deleteIndex  = findIndex(this.data.collectionList, { id: detail.id })
      const collections = this.data.collectionList
      collections.splice(deleteIndex, 1)
      this.setData({
        collectionList: collections
      })
    }catch (e) {
      throw e;
    }
  },
})
