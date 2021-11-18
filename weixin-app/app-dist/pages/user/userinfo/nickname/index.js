import regeneratorRuntime from '../../../../utils/regenerator-runtime/runtime'
const { fly, lodash, setUserInfo } = getApp()
const { isEmpty } = lodash
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nickName: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      nickName: options.nickname
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
  },
  changeNickName({detail}) {
    this.setData({
        nickName: detail.value,
      })
  },
  async submit() {
    if (!isEmpty(this.data.nickName.trim())){
      try {
        const res = await fly.post('/customer/updateCustomerInfo', {
          nickname: this.data.nickName
        })
        setUserInfo(res)
        wx.navigateBack()
        // 返回到修改资料
      }catch(e) {
        throw e
      }
    }
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})