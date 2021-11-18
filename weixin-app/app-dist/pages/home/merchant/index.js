// pages/home/merchant/index.js.js
const {globalData,fly} = getApp()
const { get, find, map } = getApp().lodash
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: `${globalData.system.height-130}px`,
    tabKey: 'product', // 页面显示tab的key
    categoryKey: null, // 显示商品分类key
    products: [],
    categoryList: null,
    cart: [],
    shoppingCartAmount: 0,
    showCart: false,
    showDetail: false,
    detail: null,
    isCollection: false,
    commentList: [],
    commentCount: {
      goodComment: 0,
      negativeComment: 0,
      imageComment: 0,
      satisficing: 0,
      totalCount: 0,
    },
    commentPagination: {
      limit: 20,
      page: 1,
      type: null,
      hasNextPage: true,
    },
    balance: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  async onLoad(options) {
    this.setData({
      options,
      tabKey: options.origin ? 'merchant' : 'product'
    })
    await this.getProducts(options)
    await this.getcart(options)
    await this.getMerchant(options)
    await this.getCommentCount(options)
    await this.getBalance()
  },
  async getBalance() {
    const res =  await fly.post('/customer/vipCard/getBalance', {
      merchantId: this.data.options.id
    })
    this.setData({
      balance:res.data.balance,
    })
  },
  onPullDownRefresh() {
    wx.stopPullDownRefresh()
  },
  changeTab(e) {
    this.setData({
      tabKey: e.detail.key
    })
    if(e.detail.key === 'comment') {
      this.getComment({
        page: this.data.commentPagination.page,
        limit: this.data.commentPagination.limit,
        tyeps: this.data.commentPagination.type,
      })
    }
  },
  changeCategory(e) {
    this.setData({
      categoryKey: e.detail.key,
      products: find(this.data.categoryList, { id: Number(e.detail.key) }).goodsList,
    })
  },
  buy() {
    wx.navigateTo({
      url: `/pages/checkout/index?merchantId=${this.data.options.id}`
    })
  },
  async getComment({page, limit, tyeps}) {
    try {
      const res = await fly.post('/merchant/getMerchantComment', {
        merchantId: this.options.id,
        page,
        limit,
        tyeps,
      })
      this.setData({
        commentList: this.data.commentPagination.page > 1 ? this.data.commentList.concat(res.data.commentList): res.data.commentList,
        commentPagination: {
          ...this.data.commentPagination,
          page: this.data.commentPagination.page + 1,
          hasNextPage: res.data.commentList.length === this.data.commentPagination.limit,
        },
      })
    }catch (e) {
      throw e;
    }
  },
  getDistance(distance) {
    return distance<1000?`${distance}m`: `${(distance/1000).toFixed(1)}km`
  },
  async getCommentCount(options) {
   try{
     const res = await fly.post(`/merchant/getCommentCount/${options.id}`)
     const { goodComment, negativeComment, imageComment} = res.data.commentCount
     this.setData({
       commentCount: {
         ...res.data.commentCount,
         totalCount: (goodComment + negativeComment + imageComment) || 0,
       }
     })
   }catch (e) {
     throw e;
   }
  },
  async getMerchant(options) {
    try{
      const nerchantDetail = await fly.post('/merchant/getMerchantDetails', {
        merchantId: options.id,
        longitude: wx.getStorageSync('longitude'),
        latitude:  wx.getStorageSync('latitude')
      })
      this.setData({
        merchantDetail: {
          ...nerchantDetail.data.merchant,
          merchantInfoImages:nerchantDetail.data.merchantInfoImages,
          distance: this.getDistance(nerchantDetail.data.merchant.distance)
        },
        isCollection:nerchantDetail.data.isCollection,
      })
    }catch (e) {
      throw e;
    }
  },
  async getProducts(options) {
    try {
      const result = await fly.post('/goods/getGoodsCategory', {
        merchantId: options.id
      })
      this.setData({
        categoryKey: get(result, 'data.categoryList.0.id'),
        products: map(get(result, 'data.categoryList.0.goodsList'), goods => ({
          ...goods,
          cartData: find(this.data.cart, {goodsId: goods.id})
        })),
        categoryList: get(result, 'data.categoryList')
      })
    }catch (e) {
      throw e;
    }
  },
  async getcart(options) {
    const cart = await fly.post('/shoppingCart/showItems', {
      merchantId: options.id,
    })
    this.setData({
      cart: cart.data.goodsList,
      shoppingCartAmount: cart.data.shoppingCartAmount,
      showCart: true,
    })
  },
  async modelAdd() {
    const params = {
      detail: {
        product: this.data.detail
      }
    }
    await this.add(params)
    this.closeDetail()
  },
  async add({ detail}) {
    try{
      const result = await fly.post('/shoppingCart/addItems', {
        goodsId: detail.product.id,
        merchantId: this.data.options.id
      })
      this.setData({
        cart: result.data.goodsList,
        shoppingCartAmount: result.data.shoppingCartAmount,
        products: map(get(this.data, 'products'), goods => ({
          ...goods,
          cartData: find(result.data.goodsList, {goodsId: goods.id})
        })),
      })
    }catch (e) {
      throw e;
    }
  },
  async update({ detail}) {
    try{
      const result = await fly.post('/shoppingCart/updateItems', {
        goodsId: detail.product.id,
        merchantId: this.data.options.id,
        number: detail.number
      })
      this.setData({
        cart: result.data.goodsList,
        shoppingCartAmount: result.data.shoppingCartAmount,
        products: map(get(this.data, 'products'), goods => ({
          ...goods,
          cartData: find(result.data.goodsList, {goodsId: goods.id})
        })),
      })
    }catch (e) {
      throw e;
    }
  },
  async clearCart({}) {
    try {
      await fly.post('/shoppingCart/removeItems', {
        merchantId: this.data.options.id
      })
      this.setData({
        cart: [],
        shoppingCartAmount: 0,
        products: map(get(this.data, 'products'), goods => ({
          ...goods,
          cartData: []
        })),
      })
    }catch (e) {
      throw e;
    }

  },
  showDetail({detail}) {
    this.setData({
      detail: detail.product,
      showDetail: true,
    })
  },
  closeDetail() {
    this.setData({
      detail: null,
      showDetail: false,
    })
  },
  async addCollection() {
   try {
     await fly.post(`customer/collection/add/${this.data.options.id}`)
     this.setData({
       isCollection: true,
     })
     wx.showToast({
       title: '收藏成功',
     })
   }catch (e) {
     throw e;
   }
  },
  async removeCollection() {
    try {
      await fly.post(`/customer/collection/delete/${this.data.options.id}`)
      this.setData({
        isCollection: false,
      })
      wx.showToast({
        title: '取消收藏成功',
      })
    }catch (e) {
      throw e;

    }
  },
  handleCommentScroll() {
    if(this.data.commentPagination.hasNextPage) {
      this.getComment(this.data.commentPagination)
    }
  },
  onShareAppMessage() {
    return {
      title: this.data.merchantDetail.name,
      path: `pages/home/merchant/index?id=${this.data.options.id}`,
    }
  },
})
