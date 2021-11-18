import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'
import { hexMD5 } from '../../utils/MD5'
const { fly, config } = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    payType: null,
    orderId: null,
    payAmount: 0,
    balance: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  async onLoad({ orderId, payAmount, merchantId }) {
    this.setData({
      orderId,
      payAmount,
      merchantId,
    })
    this.getBalance()
  },
  selectPay({currentTarget}){
    if(this.data.balance<this.data.payAmount && currentTarget.dataset.id == 0 ) { // 余额不足不能选择余额支付
      return false
    }
    this.setData({
      payType: currentTarget.dataset.id
    })
  },
  confirmPay() {
    if(this.data.payType===null) {
      return false
    }
    const message = this.data.payType == 0 ? '您将使用余额支付，是否继续？' : '您将使用微信支付，是否继续？'
    const _this = this
    wx.showModal({
      content: message,
      success({confirm}) {
        if(confirm) {
          _this.pay()
        }
      },
    })
  },
  async pay() {
    var _this = this
    try {
      const res = await fly.post('/order/pay', {
        orderId: Number(this.data.orderId),
        payType: Number(this.data.payType),
        merchantId: this.data.merchantId,
      })
      if(this.data.payType==0) { // 余额支付
        this.paySuccess({orderSn:res.data.orderSn, payAmount: res.data.payAmount})
      } else {
        // 微信支付
        const prepay_id = res.data.prepayId
        const timeStamp = toString(new Date().getTime())
        const nonceStr = Math.random().toString(36).substr(2)
        const { signType, key, appId } = config
        const paySign = hexMD5(`appId=${appId}&nonceStr=${nonceStr}&package=prepay_id=${prepay_id}&signType=${signType}&timeStamp=${timeStamp}&key=${key}`)
        wx.requestPayment({
          timeStamp: timeStamp,
          nonceStr: nonceStr,
          package: `prepay_id=${prepay_id}`,
          signType: signType,
          paySign: paySign,
          success() {
            wx.showToast({
              title: '微信支付成功',
              icon: 'success'
            })
            setTimeout(() => {
              _this.paySuccess({orderSn:res.data.orderSn, payAmount: res.data.payAmount})

            }, 300)
          },
          fail(res) {
            wx.showToast({
              title: '微信支付失败',
              icon: 'waiting'
            })
          }
        })
      }
    }catch (e) {
      throw e;
    }
  },
  async getBalance() {
    const res =  await fly.post('/customer/vipCard/getBalance', {
      merchantId: this.data.merchantId
    })
    this.setData({
      balance: Number(res.data.balance | 0),
    })
  },
  paySuccess({orderSn, payAmount}) {
    wx.redirectTo({
      url: `success/index?orderSn=${orderSn}&payAmount=${payAmount}`,
    })
  },
})
