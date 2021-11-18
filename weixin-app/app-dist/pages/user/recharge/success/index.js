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
  onLoad({ payAmount }) {
    this.setData({
      payAmount,
    })
  },
  finish() {
    wx.switchTab({
      url: "/pages/user/index",
    })
  },
})
