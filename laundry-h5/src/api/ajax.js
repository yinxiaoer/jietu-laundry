import request from '@/utils/request'

export function ajax(url, data) {
  return request({
    url: url,
    method: 'post',
    data
  })
}
