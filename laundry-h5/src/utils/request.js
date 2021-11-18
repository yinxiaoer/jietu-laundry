import axios from 'axios'
import { Message,Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import qs from 'qs'
// 创建axios实例

const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 30000, // 请求超时时间
  withCredentials:true
})
// axios.defaults.withCredentials=true
// request拦截器
service.interceptors.request.use(function (config) {
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
    if(config.method === 'post') {
        config.data = qs.stringify( {
            ...config.data
        })
    }
    return config;
  }, function (error) {
    loadinginstace.close()
    return Promise.reject(error);
  })

// respone拦截器
service.interceptors.response.use(
  response =>{
    if(response.data.status==401){
      //未登录
      Message({
        message: response.data.message,
        type: 'error'
      })
      store.dispatch('FedLogOut').then(() => {
          location.reload();// 为了重新实例化vue-router对象 避免bug
      });
      return Promise.reject('error');
    }
    if(response.data.status==403){
      //没有权限
      Message({
        message: response.data.message,
        type: 'error'
      })
      return Promise.reject('error');
    }
    if(response.data.status==404){
      //404请求接口不存在
      Message({
        message: response.data.message,
        type: 'error'
      })
       return Promise.reject('error');
    }
    if(response.data.status==500){
      Notification.error({
        title: '错误',
        message: "系统错误",
        position: 'bottom-right'
      });
      return Promise.reject('error');
    }
    if(response.data.status==400){
      Notification.error({
        title: '错误',
        message: response.data.message,
        position: 'bottom-right'
      });
      return Promise.reject('error');
    }
    if(response.data.status==200){
      return response;
    }
  },
  error => {
    console.log('err' + error)// for debug
    Message({
      message: error.response.data.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error)
  })

export default service
