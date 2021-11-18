import regeneratorRuntime from './regenerator-runtime/runtime'
var Fly = require("../lib/fly.js")//引入路径根据自己放置的目录配置
var fly = new Fly;

// const { getToken } = getApp()

import {config} from '../config/index'
import {ERROR_CODE} from '../constant/ERROR_CODE'

fly.interceptors.request.use((request, a) => {
    request.timeout = config.timeout;
    request.baseURL = config.baseURL;
    if (wx.getStorageSync(`token${config.tokenKey}`)) {//检查本地缓存是否有token存在没有则重新获取
        request.headers = {//设置请求头
            "content-type": request.header?request.header.contentType:"application/json",
            "token": wx.getStorageSync(`token${config.tokenKey}`)
        }
        wx.showLoading({
            mask: true,
        });
        return request;
    } else {
        wx.reLaunch({
            url: '/pages/authorization/index'
        })
    }
})

fly.interceptors.response.use(
    (response) => {
        wx.hideLoading();
        if(response.data.code==='0000'){
            return Promise.resolve(response.data)
            // return response.data;//请求成功之后将返回值返回
        } else {
            // 统一错误处理
            if(ERROR_CODE.indexOf(response.data.code) === -1){
                wx.showToast({
                    title: response.data.msg,
                })
            }
            // 单独错误处理
            if(response.data.code === '2001') { // 地理位置授权
                wx.showModal({
                    content: '衣物取送需要您的地理位置',
                    confirmText: '去开启',
                    success({confirm}) {
                        if(confirm) {
                            wx.openSetting({
                                success(res) {
                                    if(res.authSetting['scope.userLocation']) {
                                        wx.getLocation({
                                            altitude: true,
                                            type: 'gcj02',
                                            async success(local) {
                                                wx.setStorage({
                                                    key : 'latitude',
                                                    data : local.latitude})
                                                wx.setStorage({
                                                    key : 'longitude',
                                                    data : local.longitude})
                                            }
                                        })
                                    }
                                },
                            })
                        }
                    },
                })
            }
            if(response.data.code==="2000") { // 未登录
                wx.removeStorageSync(`token${config.tokenKey}`)
                wx.reLaunch({
                    url: '/pages/authorization/index'
                })
            }
            return Promise.reject(response.data)
        }

    },
    (err) => {
        //请求出错，根据返回状态码判断出错原因
        wx.hideLoading();
        // Do something with response error
    }
)
export default {
    fly: fly
}
