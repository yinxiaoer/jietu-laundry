// pages/payment/success/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad({ orderSn, payAmount }) {
    this.setData({
      orderSn,
      payAmount,
    })
  },
  finish() {
    wx.switchTab({
      url: "/pages/home/index",
    })
  },
})
