// components/merchant/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    detail: {
      type: Object,
      value: {},
    },
    balance: {
      type: Number,
      value: 0,
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    imgList: [{
      id: 1,
      image: {
        url: 'https://uxresources.baozun.com/uat/88000111/png/20181218/9ab2e910f91d4383bc14d051ef03af10.png'
      }
    }, {
      id: 2,
      image: {
        url: 'https://uxresources.baozun.com/uat/88000111/jpg/20181218/e718e66ab38d46d1b9bbdb165d62c93d.jpg'
      }
    }, {
      id: 3,
      image: {
        url: 'https://uxresources.baozun.com/uat/88000111/gif/20181129/dfe33247486e4eb1be4f938e606e36f6.gif'
      }
    }, {
      id: 4,
      image: {
        url: 'https://uxresources.baozun.com/uat/88000111/jpg/20181126/341512ed4d68477e9c923cab2837455a.jpg'
      }
    }]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    goRecharge() {
      wx.navigateTo({
        url: `/pages/user/recharge/index?merchantId=${this.data.detail.id}`
      })
    },
  }
})
