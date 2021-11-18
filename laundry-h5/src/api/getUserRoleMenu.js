import request from '@/utils/request'

export default function getUserRoleMenu() {
  return request({
    url: '/resource/getUserRoleMenu',
    method: 'post'
  })
}


