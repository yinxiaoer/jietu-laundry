import request from '@/utils/request'

export function loginByUsername(username, password) {
  var data = {
    username,
    password
  }
  //data = JSON.stringify(data)
  return request({
    //url: '/login/login',
    url:'/login',
    method: 'post',
    data
  })
}


export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function getUserInfo() {
  return request({
    url: '/getCurrentUser', //获取用户信息
    method: 'post'
  })
}

