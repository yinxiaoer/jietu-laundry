import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'

const { fly, config, lodash } = getApp()
Page({
  onLoad() {
    wx.setNavigationBarColor({
      backgroundColor: '#ffffff',
      frontColor: '#000000',
    })
    const tokenKey = config.tokenKey
    const token = wx.getStorageSync(`token${tokenKey}`)
    if(!lodash.isEmpty(token)) {
      wx.reLaunch({
        url: '/pages/home/index'
      })
    }
  },
  getNumber({ detail }) {
    const { encryptedData, iv } = detail
    if(iv) {
      this.getToken({
        encryptedData,
        iv,
      })
    }
  },
  getToken({encryptedData, iv}) {
     wx.login({
      async success(res) {
        if (res.code) {
          try {
            const loginRes = await fly.post(`${config.baseURL}/customer/authorization`, {
              code: res.code,
            })
            const tokenKey = config.tokenKey
            wx.setStorageSync(`token${tokenKey}`, loginRes.data.token)
            console.log(wx.getStorageSync(`token${tokenKey}`));
            try {
              // 登录成功
              await fly.post(`${config.baseURL}/customer/saveWeixinPhone`, {
                encryptedData,
                iv,
              })
              wx.showToast({
                title: '登录成功',
                icon: null,
              })
              setTimeout(() => {
                wx.reLaunch({
                  url: '/pages/home/index'
                })
              }, 500)
            }catch (e) {
              // 登录失败， 清除token
              wx.removeStorageSync(`token${tokenKey}`)
            }
          }catch (e) {
            console.log(e);
            // 登录失败
            wx.removeStorageSync(`token${tokenKey}`)
          }
        }
      },
    });
  },
})
