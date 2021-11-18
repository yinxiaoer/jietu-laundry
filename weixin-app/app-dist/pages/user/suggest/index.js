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
    tags: [],
    selectedTag: '',
    fileList: [],
    comment: '',
    formData: {
      uploadPath: 'customer/headerImg'
    },
    header: {
      'token': wx.getStorageSync(`token${config.tokenKey}`),
    },
  },

  onLoad() {
    this.getFeedbackType()
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
      let fileList = this.data.fileList;
      fileList.push({
        uid: new Date().getTime(),
        uploadImageUrl: res.data.url,
        url: config.ossBaseURL + res.data.url,
      })
      this.setData({
        fileList: fileList,
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
    const Index = findIndex(this.data.fileList, file => file.uid == e.detail.file.uid)
    let fileList = this.data.fileList
    fileList.splice(Index, 1)
    this.setData({
      fileList: fileList,
    })
  },
  selectedTag({detail}) {
    this.setData({
      selectedTag: detail.dataSet,
    })
  },
  commentChange({detail}) {
    this.setData({
      comment: detail.value,
    })
  },
  async getFeedbackType() {
   const res = await fly.post('/feedback/getFeedbackType')
    this.setData({
      tags: res.data.FeedbackTypeList
    })
  },
  async onSubmit() {
    const imageList = this.data.fileList.map((file, index) => ({
      sortOrder: index,
      url: file.uploadImageUrl,
    }))
    try {
      await fly.post('/feedback/save', {
        imageList,
        typeId: this.data.selectedTag,
        content: this.data.comment,
      })
      await wx.showToast({
        title: '反馈成功',
      })
      this.setData({
        selectedTag: '',
        comment: '',
        fileList: [],
      })
      setTimeout(() => {
        wx.navigateBack()
      }, 400)
    }catch (e) {
      throw e;
    }
  },
})
