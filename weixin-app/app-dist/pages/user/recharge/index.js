// pages/user/recharge/index.js
import regeneratorRuntime from '../../../utils/regenerator-runtime/runtime'
import { wxPay } from '../../../utils/wxPay'
const { fly, getUserBaseInfo, globalData, lodash } = getApp()
const { get, isEmpty } = lodash

Page({
  data: {
    amount: null,
    topUpList: [],
    selectedId: '',
  },
  async onLoad({merchantId}) {
    await getUserBaseInfo()
    this.setData({
      userInfo: get(globalData, 'userInfo'),
      merchantId,
    });
    this.getRechargeList()
  },
  async recharge(){
    try {
      const payData = await fly.post('/topUp/pay', {
        amount: this.data.amount,
        merchantId: this.data.merchantId,
      })
      wxPay({prepayId: payData.data.prepayId}, ({success}) => {
        if(success) {
            wx.showToast({
              title: '余额充值成功',
              icon: 'success'
            })
            setTimeout(() => {
              wx.redirectTo({
                url: `success/index?payAmount=${this.data.amount}`,
              })

            }, 300)
        } else {
            wx.showToast({
              title: '余额充值失败',
              icon: 'waiting'
            })
        }
      })
    }catch (e) {
      console.log(e);
      wx.showToast({
        title: '余额充值失败',
        icon: 'waiting'
      })
    }
  },
  async getRechargeList() {
    const res = await fly.post('/topUp/list', {
      merchantId: this.data.merchantId
    })
    this.setData({
      topUpList: res.data.topUpList
    })
  },
  selectedRecharge({ detail }) {
    if(detail === '自定义') {
      this.setData({
        selectedId: '0',
        amount: null,
      })
    } else {
      this.setData({
        selectedId: detail.dataSet.seq,
        amount: detail.dataSet.topUpAmount,
      })
    }
  },
  setDefineMoney({detail}) {
    const money = !!Number(detail.value.trim())
      if(money) {
        this.setData({
          amount: isEmpty(detail.value.trim()) ? null : detail.value.trim(),
        })
      }
  },
})
