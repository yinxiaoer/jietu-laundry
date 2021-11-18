const { globalData, getAddress, fly, setAddress } = getApp()
const { sortBy } = getApp().lodash
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'

Page({
  data: {
    delBtnWidth: 360,
    system: globalData.system
  },
  async onShow() {
    await getAddress()
    this.setData({
      addresses: globalData.addresses || []
    })
  },
  addAddress() {
    wx.navigateTo({
      url: 'update/index'
    })
  },
  updateAddress({currentTarget}){
    wx.navigateTo({
      url: `update/index?id=${currentTarget.dataset.id}`
    })
  },
  async delAddress({currentTarget}) {
    const { id, disabled } = currentTarget.dataset;
    if( disabled ) {
      return false
    }
    try {
      const res = await fly.post(`/customer/deleteAddress/${id}`)
      this.setData({
        addresses: res.data.addresses,
      })
      setAddress(res.data.addresses)
    }catch (e) {
      throw e;
    }
  },
  async setDefault({currentTarget}) {
    const { id, disabled } = currentTarget.dataset;
    if( disabled ) {
      return false
    }
    try {
      const res = await fly.post(`/customer/setDefaultedAddress/${id}`)
      this.setData({
        addresses: sortBy(res.data.addresses, address => !address.defaulted),
      })
      setAddress(res.data.addresses)
    }catch (e) {
      throw e;
    }
  },
  touchS: function (e) {
    if (e.touches.length == 1) {
      this.setData({
        //设置触摸起始点水平方向位置
        startX: e.touches[0].clientX
      });
    }
  },
  touchM: function (e) {
    if (e.touches.length == 1) {
      //手指移动时水平方向位置
      var moveX = e.touches[0].clientX;
      //手指起始点位置与移动期间的差值
      var disX = this.data.startX - moveX;
      var delBtnWidth = this.data.delBtnWidth;
      var txtStyle = "";
      if (disX == 0 || disX < 0) {//如果移动距离小于等于0，文本层位置不变
        txtStyle = "left:0rpx";
      } else if (disX > 0) {//移动距离大于0，文本层left值等于手指移动距离
        txtStyle = "left:-" + disX + "rpx";
        if (disX >= delBtnWidth) {
          //控制手指移动距离最大值为删除按钮的宽度
          txtStyle = "left:-" + delBtnWidth + "rpx";
        }
      }
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.addresses;
      //更新列表的状态
      this.setData({
        addresses: list.map((item,itemIndex)=> {
          if(itemIndex === index) { // 当前展开行
            return {
              ...item,
              txtStyle,
            }
          }else {
            return {
              ...item,
              txtStyle: null,
            }
          }
        })
      });
    }
  },
  touchE: function (e) {
    if (e.changedTouches.length == 1) {
      //手指移动结束后水平位置
      var endX = e.changedTouches[0].clientX;
      //触摸开始与结束，手指移动的距离
      var disX = this.data.startX - endX;
      var delBtnWidth = this.data.delBtnWidth;
      //如果距离小于删除按钮的1/2，不显示删除按钮
      var txtStyle = disX > delBtnWidth / 2 ? "left:-" + delBtnWidth + "rpx" : "left:0rpx";
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.addresses;
      list[index].txtStyle = txtStyle;
      //更新列表的状态
      this.setData({
        addresses: list,
      });
    }
  },
  removeOpen() {
    this.setData({
      addresses: this.data.addresses.map(address => ({
        ...address,
        txtStyle: null,
      })),
    });
  },
})
