import {$wuxToast} from "../../../packages/@wuxui/index";
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'

const { config, fly }  = getApp()
const { findIndex }  = getApp().lodash

Page({

  /**
   * 页面的初始数据
   */
  data: {
    config,
    formData: {
      uploadPath: 'customer/headerImg',
    },
    header: {
      'token': wx.getStorageSync(`token${config.tokenKey}`),
    },
    fileList: [],
    commentData: {
      orderId: null,
      grade: null,
      comment: '',
      imageList: []
    },
  },

  onLoad: function (options) {
    this.setData({
      commentData: {
        ...this.data.commentData,
        orderId: options.id,
      },
    })
    this.fetchOrderDetail(options.id)
  },
  async fetchOrderDetail(orderId) {
    const response = await fly.post('/order/getOrderDetails', {orderId})
    this.setData({
      orderDetail: response.data.order,
    })
  },
  uploadChange() {
  },
  uploadSuccess(){
  },
  uploadComplete(el) {
    const res = JSON.parse(el.detail.data)
    if(res.code==='0000') {
      $wuxToast().show({
        type: 'success',
        duration: 1500,
        color: '#fff',
        text: '上传图片成功',
      })
      let imageList = this.data.commentData.imageList;
      let fileList = this.data.fileList;
      fileList.push({
        uid: new Date().getTime(),
        url: config.ossBaseURL + res.data.url,
      })
      imageList.push({
        uid: new Date().getTime(),
        url: res.data.url,
      })
      this.setData({
        fileList: fileList,
        commentData: {
          ...this.data.commentData,
          imageList: imageList,
        },
      })
    }else {
      $wuxToast().show({
        type: 'forbidden',
        duration: 1500,
        color: '#fff',
        text: '上传图片失败',
      })
    }
  },
  uploadFail() {
    $wuxToast().show({
      type: 'forbidden',
      duration: 1500,
      color: '#fff',
      text: '上传图片失败',
    })
  },
  remove(e) {
    const Index = findIndex(this.data.commentData.imageList, file => file.uid == e.detail.file.uid)

    let imageList = this.data.commentData.imageList
    let fileList = this.data.fileList
    fileList.splice(Index, 1)
    imageList.splice(Index, 1)
    this.setData({
      fileList: fileList,
      commentData: {
        ...this.data.commentData,
        fileList: imageList,
      },
    })
  },
  changeCommentCon({detail}) {
    this.setData({
      commentData: {
        ...this.data.commentData,
        comment: detail.value,
      },
    })
  },
  updateRater({detail}) {
    this.setData({
      commentData: {
        ...this.data.commentData,
        grade: detail.value,
      },
    })
  },
  async submit() {
    const form = {
      ...this.data.commentData,
      imageList: this.data.commentData.imageList.map((item, index) => ({
        ...item,
        sortOrder: index,
      }))
    }
    try {
     await fly.post('/order/comment', form)
      await wx.showToast({
        title: '评论成功',
      })
      this.setData({
        fileList: [],
        commentData: {
          orderId: null,
          grade: null,
          comment: '',
          imageList: []
        },
      })
      setTimeout(() => {
        wx.switchTab({
          url: '/pages/order/index'
        })
      }, 400)
    }catch (e) {
      throw  e;
    }
  },
})
