import Cookies from 'js-cookie'

const TokenKey = 'JSESSIONID';//token

export function getToken() {
  return Cookies.get('isLogin')
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
