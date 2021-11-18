// pages/user/info/index.js
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'

const { getUserBaseInfo, globalData, config, setUserInfo, fly, lodash} = getApp()
const { isEmpty, get } = lodash

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    config,
    fileList: [],
    formData: {
      uploadPath: 'customer/headerImg'
    },
    header: {
      'token': wx.getStorageSync(`token${config.tokenKey}`),
    },
    sexArray: [{
      label: '男',
      value: '1'
    },
    {
      label: '女',
      value: '2'
    }],
    picWays: [{
      label: '上传头像',
      value: '3'
    }],
    choice:[],
    visible:false,
    toptitle:''
  },
  async onLoad() {
    await getUserBaseInfo();
    this.setData({
      userInfo: get(globalData, 'userInfo')
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onShow(){
    this.setData({
      userInfo: get(globalData, 'userInfo')
    })
  },
  changeNickname(){
    const nickname = this.data.userInfo.nickname
    wx.navigateTo({
      url: 'nickname/index?nickname=' + nickname
    })
  },
  selectsex(){
    this.setData({
      choice: this.data.sexArray,
      visible:true,
      toptitle: '请选择性别'
    })
  },
  selectpic() {
    this.setData({
      choice: this.data.picWays,
      visible: true,
      toptitle: ''
    })
  },
  close(e) {
    this.setData({
      visible: false,
    })
  },
  async chooseclosed({detail}){
    if (detail === '男' || detail === '女'){
      if (!isEmpty(detail.trim())){
        try{
          const res = await fly.post('/customer/updateCustomerInfo', {
            sex: detail
          })
          setUserInfo(res);
          this.setData({
            userInfo: get(globalData, 'userInfo')
          })
        }catch (e) {
          throw e
        }
      }
    }

  },
  loginOut() {
    wx.showModal({
      content: '您将注销您的登录，是否继续？',
      confirmText: '继续',
      success({confirm}) {
        if(confirm) {
          wx.removeStorageSync(`token${config.tokenKey}`)
          wx.reLaunch({
            url: '/pages/authorization/index'
          })
        }
      },
    })
  },
})
