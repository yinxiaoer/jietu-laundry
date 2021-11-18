'use strict';
import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'
Object.defineProperty(exports, "__esModule", {
    value: true
});

const {getUserBaseInfo, globalData, config, fly, lodash} = getApp()
const {get}  = lodash

exports.default = Page({
    data: {
        '__code__': {
            readme: ''
        },
        token: '',
        userInfo: {},
        config,
    },
    goAddress() {
        wx.navigateTo({
            url: 'address/index'
        })
    },
    goUserLaundry() {
        wx.navigateTo({
            url: 'userLaundry/index'
        })
    },
    goSuggest() {
        wx.navigateTo({
            url: 'suggest/index'
        })
    },
    goInfoEdit() {
        wx.navigateTo({
            url: 'userinfo/index'
        })
    },
    goCollection() {
        wx.navigateTo({
            url: 'collection/index'
        })
    },
    goHistory() {
        wx.navigateTo({
            url: 'history/index'
        })
    },
    async onShow(){
        await getUserBaseInfo()
        this.setData({
        userInfo: get(globalData, 'userInfo'),
      });
    },
    async getBalance() {
       const res =  await fly.post('/customer/getBalance')
        this.setData({
            balance: res.data.balance,
        })
    }
});
