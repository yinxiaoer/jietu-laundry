import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'

import { $wuxBackdrop} from '../../packages/@wuxui/index'
const { fly, config, setUserInfo } = getApp()

const prefixCls = 'wux-animate'

Component({
  externalClasses: ['wux-class'],
  options: {
    multipleSlots: true,
  },
  properties: {
    position: {
      type: String,
      value: 'center',
      observer: 'getTransitionName',
    },
    mask: {
      type: Boolean,
      value: true,
    },
    maskClosable: {
      type: Boolean,
      value: true,
    },
    visible: {
      type: Boolean,
      value: false,
      observer: 'setPopupVisible',
    },
    zIndex: {
      type: Number,
      value: 999,
    },
    chooseArray:{
      type: Object,
      value:''
    },
    title:{
      type: String,
      value: '',
    }
  },
  data: {
    transitionName: '',
    popupVisible: false,
    portrait: '',
    config,
    fileList: [],
    formData: {
      uploadPath: 'customer/headerImg'
    },
    header: {
      'token': wx.getStorageSync(`token${config.tokenKey}`),
    },
  },
  methods: {
    /**
     * 点击关闭按钮事件
     */
    close() {
      this.triggerEvent('close')
    },
    /**
     * 点击蒙层事件
     */
    onMaskClick() {
      if (this.data.maskClosable) {
        this.close()
      }
    },
    chooseitem({currentTarget}){
      this.triggerEvent('chooseclose', currentTarget.dataset.name)
      this.close()
    },
    uploadBefore() {
      wx.showLoading({
        mask: true,
      })
    },
    async uploadComplete({detail}) {
      const uploadRes = JSON.parse(detail.data)
        try {
          const res = await fly.post('/customer/updateCustomerInfo', {
            portrait: uploadRes.data.url
          })
          wx.hideLoading({
            mask: true,
          })
          setUserInfo(res);
          this.close()
          wx.showToast({
            title: '上传头像成功',
            icon: 'success',
            duration: 2000
          })
        } catch (e) {
          wx.hideLoading({
            mask: true,
          })
          wx.showToast({
            title: '上传头像失败',
            icon: 'fail',
            duration: 2000
          })
        }
    },
    uploadFail() {
      wx.hideLoading({
        mask: true,
      })
      wx.showToast({
          title: '上传头像失败',
          icon: 'fail',
          duration: 2000
        })
    },
    /**
     * 组件关闭后的回调函数
     */
    onExited() {
      this.triggerEvent('closed')
    },
    /**
     * 获取过渡的类名
     */
    getTransitionName(value = this.data.position) {
      let transitionName = ''

      switch (value) {
        case 'top':
          transitionName = `${prefixCls}--slideInDown`
          break
        case 'right':
          transitionName = `${prefixCls}--slideInRight`
          break
        case 'bottom':
          transitionName = `${prefixCls}--slideInUp`
          break
        case 'left':
          transitionName = `${prefixCls}--slideInLeft`
          break
        default:
          transitionName = `${prefixCls}--fadeIn`
          break
      }

      this.setData({ transitionName })
    },
    /**
     * 设置 popup 组件的显示隐藏
     */
    setPopupVisible(popupVisible) {
      if (this.data.popupVisible !== popupVisible) {
        this.setData({ popupVisible })
        this.setBackdropVisible(popupVisible)
      }
    },
    /**
     * 设置 backdrop 组件的显示隐藏
     */
    setBackdropVisible(visible) {
      if (this.data.mask && this.$wuxBackdrop) {
        this.$wuxBackdrop[visible ? 'retain' : 'release']()
      }
    },
  },
  created() {
    if (this.data.mask) {
      this.$wuxBackdrop = $wuxBackdrop('#wux-backdrop', this)
    }
  },
  attached() {
    this.setPopupVisible(this.data.visible)
    this.getTransitionName()
  },
})
