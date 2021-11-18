import { loginByUsername, logout, getUserInfo } from '@/api/login'
import {Message} from 'element-ui'
const user = {
  state: {
    user: '',
    status: '',
    code: '',
    name: '',
    avatar: '',
    introduction: '',
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      const userName = userInfo.userName.trim()
      return new Promise((resolve, reject) => {
        loginByUsername(userName, userInfo.password).then(response => {
          if(response.data.status==200){
            sessionStorage.setItem('isLogin',true)
             Message({
               message:response.data.message,
               type:'success'
             })
              resolve()
          }else{
             Message({
               message:response.data.message,
               type:'error'
             })
            reject();
          }
          const data = response.data

        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          if (!response.status==200) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          }
          var data=response.data.obj;
          commit('SET_NAME', data.username)
          commit('SET_AVATAR', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
          commit('SET_INTRODUCTION', data.realname)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          sessionStorage.clear()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        resolve()
      })
    },

    // 动态修改权限
    // ChangeRole({ commit }, role) {
    //   return new Promise(resolve => {
    //     commit('SET_TOKEN', role)
    //     setToken(role)
    //     getUserInfo(role).then(response => {
    //       const data = response.data
    //       commit('SET_ROLES', data.role)
    //       commit('SET_NAME', data.name)
    //       commit('SET_AVATAR', data.avatar)
    //       commit('SET_INTRODUCTION', data.introduction)
    //       resolve()
    //     })
    //   })
    // }
  }
}

export default user
