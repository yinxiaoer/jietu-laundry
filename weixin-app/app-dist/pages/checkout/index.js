const {fly} = getApp()
const { find, map, isEmpty } = getApp().lodash
import comment from '../../utils/comment'
import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    remark: null,
    prices: null, // 邮费
    takeInitDate: comment(new Date()).format('YYYY-MM-DD'),
    sendInitDate: null,
    visibleStartDateSelected: false,
    visibleEndDateSelected: false,
    visibleAddressSelected: false,
    timeList: [],
    form: {
      takeStartDate: null,
      takeEndDate: null,
      sendStartDate: null,
      sendEndDate: null,
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  async onLoad(options) {
    wx.setNavigationBarColor({
      backgroundColor: '#FFFFFF',
      frontColor: '#000000'
    })
    this.setData({
      options,
    })
    await this.getcart(options)
    await this.getAddress()
    // TODO 请求购物车计算结果
    await this.cartItemCalculate()
    // TODO 请求购物车计算结果
  },
  async getcart(options) {
   try {
     const cart = await fly.post('/shoppingCart/showItems', {
       merchantId: options.merchantId,
     })
     this.setData({
       cart: cart.data.goodsList,
     })
   }catch (e) {
     throw e;
   }
  },
  async cartItemCalculate() {
    try{
      const result = await fly.post('/order/cartItemCalculate', {
        goodsList: map(this.data.cart, good => ({
          goodsId: good.goodsId,
          number: good.number,
        })),
      })
      this.setData({
        prices: result.data
      })
    }catch (e) {
      throw e;
    }
  },
  async getAddress() {
    const address = await fly.post('/customer/getAddresses')
    this.setData({
      addresses: address.data.addresses,
      defaultAddress: find(address.data.addresses, {defaulted: true})
    })
  },
  async getTimeList() {
    const res = await fly.post('/merchant/getDistributionTimeList', {
      merchantId: this.data.options.merchantId,
    })
    this.setData({
      timeList: res.data.distributionTimeList,
    })
  },
  async onShow() {
    await this.getAddress()
    await this.getTimeList()
  },
  updateRemark({detail}) {
    this.setData({
      remark: detail.value
    })
  },
  async createOrder() {
    if( isEmpty(this.data.form.sendEndDate)) {
      return false
    }
    const formData = {
      customerAddressId: this.data.defaultAddress.id,
      orderGoodsList: map(this.data.cart, good => ({
        goodsId: good.goodsId,
        price: good.price,
        number: good.number
      })),
      buyerRemark: this.data.remark,
      goodsTailAmount: this.data.prices.goodsTailAmount,
      discountAmount: this.data.prices.discountAmount,
      freightFee: this.data.prices.freightFee,
      payAmount: this.data.prices.payAmount,
      currencyCode: this.data.prices.currencyCode,
      merchantId: Number(this.data.options.merchantId),
      fetchStartDate: this.data.form.takeStartDate,
      fetchEndDate: this.data.form.takeEndDate,
      deliverStartDate: this.data.form.sendStartDate,
      deliverEndDate: this.data.form.sendEndDate
    }
    try {
      const result = await fly.post('/order/createOrder', formData)
      wx.navigateTo({
        url: `/pages/payment/index?orderId=${result.data.orderId}&payAmount=${result.data.payAmount}&merchantId=${this.data.options.merchantId}`,
      })

    }catch (e) {
      throw e;
    }
  },
  showDateStartSeleted() {
    this.setData({
      visibleStartDateSelected: true,
    })
  },
  showDateEndSeleted() {
    if(this.data.sendInitDate) {
      this.setData({
        visibleEndDateSelected: true,
      })
    }
  },
  closeDateSelected({detail}) {
    this.setData({
      visibleStartDateSelected: false,
      sendInitDate: detail.startDate ? comment(detail.startDate).add(3, 'd').format('YYYY-MM-DD HH:mm:ss'):false,
      form: {
        takeStartDate: comment(detail.startDate).format('YYYY-MM-DD HH:mm:ss'),
        takeEndDate: comment(detail.endDate).format('YYYY-MM-DD HH:mm:ss'),
        sendStartDate: null,
        sendEndDate: null,
      },
    })
  },
  closeEndDateSelected({detail}) {
    this.setData(
        {
          visibleEndDateSelected: false,
          sendInitDate: detail.startDate,
          form: {
            takeStartDate: comment(this.data.form.takeStartDate).format('YYYY-MM-DD HH:mm:ss'),
            takeEndDate: comment(this.data.form.takeEndDate).format('YYYY-MM-DD HH:mm:ss'),
            sendStartDate: comment(detail.endDate).format('YYYY-MM-DD HH:mm:ss'),
            sendEndDate: comment(detail.endDate).format('YYYY-MM-DD HH:mm:ss'),
          }
        }
    )
  },
  closeAddressSelected() {
    this.setData({
      visibleAddressSelected: false,
    })
  },
  showAddressSelected() {
    this.setData({
      visibleAddressSelected: true,
    })
  },
  updateAddress({detail}) {
    this.setData({
      defaultAddress: find(this.data.addresses, {id: Number(detail)})
    })
  },
  addAddress(){
   wx.navigateTo({
      url: '/pages/user/address/update/index'
    })
  },
})
