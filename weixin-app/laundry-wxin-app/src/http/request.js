
const Fly = require("../access/js/wx.umd.min")//引入路径根据自己放置的目录配置
const fly = new Fly;
import {config} from '../config'
// const { getToken } = getApp()
// 定义公共配置
console.log(config);

fly.config = config
fly.interceptors.request.use((request) => {
    request.timeout = config.timeout;

    if (wx.getStorageSync('token')) {//检查本地缓存是否有token存在没有则重新获取
        request.headers = {//设置请求头
            "content-type": "application/json",
            "token": wx.getStorageSync('token')
        }
        return request;
    } else {
        fly.lock();//锁住请求
        return getApp().getToken().then(res => {//重新获取token
            request.timeout = 30000,
                request.headers = {//设置请求头
                    "content-type": "application/json",
                    "token": wx.getStorageSync('token')
                }
            wx.showLoading({
                title: "加载中",
                mask: true,
            });
            fly.unlock();//解锁请求
            return request;//继续之前的请求
        })
    }
})

fly.interceptors.response.use(
    (response) => {
        wx.hideLoading();
        // TODO 请求成功后统一处理errorCode
        return response;//请求成功之后将返回值返回
    },
    (err) => {

        //TODO 请求出错统一处理
        //请求出错，根据返回状态码判断出错原因
        console.log(err)
        wx.hideLoading();
        // Do something with response error
    }
)
module.exports = {
    fly,
}
