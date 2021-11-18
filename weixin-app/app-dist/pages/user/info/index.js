// pages/user/info/index.js
import { $wuxSelect, $wuxToast } from '../../../packages/@wuxui/index'
import { IMG_SIZE, IMG_TYPE } from '../../../constant/FILE'
const { globalData, fly, config, getFileType, setUserInfo }  = getApp()


Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    config,
    fileList: [],
    isValidate: true,
    formData: {
      uploadPath: 'customer/headerImg'
    },
    header: {
      'token': wx.getStorageSync(`token${config.tokenKey}`),
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    this.setData({
      userInfo: globalData.userInfo
    })
  },
  uploadChange() {

  },
  uploadSuccess(){

  },
  uploadComplete(el) {
    const res = JSON.parse(el.detail.data)
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        avatarUrl: `${config.ossBaseURL}${res.data.url}`,
      }
    })
    if(res.code==='0000') {
      $wuxToast().show({
        type: 'success',
        duration: 1500,
        color: '#fff',
        text: '上传头像成功',
      })
      this.setData({
        userInfo: {
          ...this.data.userInfo,
          portrait: `${config.ossBaseURL}${res.data.url}`
        },
      })
    }else {
      $wuxToast().show({
        type: 'forbidden',
        duration: 1500,
        color: '#fff',
        text: '上传头像失败',
      })
    }
  },
  uploadFail() {
    $wuxToast().show({
      type: 'forbidden',
      duration: 1500,
      color: '#fff',
      text: '上传头像失败',
    })
  },
  uploadPicBefore(el){
    // if(el.detail.tempFiles.size/1024/1024>IMG_SIZE) {
    //   this.setData({isValidate: false})
    // } else
    // if(!IMG_TYPE.includes(getFileType(el.detail.tempFiles[0].path))) {
    //   this.setData({isValidate: false})
    // } else {
    //   this.setData({isValidate: true})
    // }
  },
  changeNickName(el) {
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        nickName: el.detail.value,
      },
    })
  },
  changePhone(el) {
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        mobile: el.detail.value,
      },
    })
  },
  changeIdCard(el) {
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        idCard: el.detail.value,
      },
    })
  },
  changeEmail(el) {
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        email: el.detail.value,
      },
    })
  },
  changeSex(el) {
    $wuxSelect('#wux-select').open({
      value: this.data.userInfo.sex,
      options: [
        '男',
        '女',
        '保密',
      ],
      onConfirm: (value, index, options) => {
        if (index !== -1) {
          this.setData({
            userInfo: {
              ...this.data.userInfo,
              sex: value,
            },
          })
        }
      },
    })
  },
  save() {
    fly.post('/customer/updateCustomerInfo', {
      nickname: this.data.userInfo.nickName,
      idCard: this.data.userInfo.idCard,
      mobile: this.data.userInfo.mobile,
      sex: this.data.userInfo.sex,
      email: this.data.userInfo.email,
      portrait: this.data.userInfo.portrait,
    }).then((res)=>{
      setUserInfo(res)
      $wuxToast().show({
        type: 'forbidden',
        duration: 1500,
        color: '#fff',
        text: '保存成功',
      })
    })
  },
})
