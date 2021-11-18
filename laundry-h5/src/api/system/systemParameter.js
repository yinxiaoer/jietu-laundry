import request from '@/utils/request'

//获取系统参数类型列表
export function typeList(data) {
  return request({
    url:'/systemParameterType/list',
    method: 'post',
     data
  })
}
//保存系统参数类型
export function saveType(data) {
  return request({
    url:'/systemParameterType/save',
    method: 'post',
    data
  })
}
//更新系统参数类型
export function updateType(data) {
  return request({
    url:'/systemParameterType/update',
    method: 'post',
    data
  })
}
//删除系统参数类型
export function deleteTypeById(data) {
  return request({
    url:'/systemParameterType/delete',
    method: 'post',
    params: data
  })
}

export function getAllType() {
  return request({
    url:'/systemParameterType/getAll',
    method: 'post',
  })
}

//获取系统参数列表
export function list(data) {
  return request({
    url:'/systemParameter/list',
    method: 'post',
    params: data
  })
}
//保存系统参数
export function save(data) {
  return request({
    url:'/systemParameter/save',
    method: 'post',
    params: data
  })
}
//更新系统参数
export function update(data) {
  return request({
    url:'/systemParameter/update',
    method: 'post',
    params: data
  })
}
//删除系统参数类型
export function deleteById(data) {
  return request({
    url:'/systemParameter/delete',
    method: 'post',
    params: data
  })
}

export function findParameterByTypeId(data) {
  return request({
    url:'/systemParameter/findByType',
    method: 'post',
    data
  })
}

