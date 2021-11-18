import request from '@/utils/request'

//用户管理
export function userList(data) {
  return request({ //请求用户列表
    url:'/user/list',
    method: 'post',
    data
  })
}

export function saveUser(data){
  return request({ // 添加用户
    url:'/user/save',
    method:'post',
    data
  })
}
export function deleteById(data){
  return request({ //删除单个用户
    url:'/user/delete',
    method:'post',
    data
  })
}

export function updateUser(data){ // 修改用户
  return request({
    url:'/user/update',
    method:'post',
    data
  })
}

export function updatePwd(data){ // 修改密码
  return request({
    url:'/user/updatePassword',
    method:'post',
    data
  })
}

export function resetPwd(data){ // 修改密码
  return request({
    url:'/user/resetPassword',
    method:'post',
    data
  })
}


// 角色管理
export function roleList(data){
  return request({ // 请求角色列表
    url:'/role/list',
    method:'post',
    data
  })
}

export function roleSave(data){
  return request({ //保存角色
    url:'/role/save',
    method:'post',
    data
  })
}


export function roleUpdate(data){
  return request({ //更新角色
    url:'/role/update',
    method:'post',
    data
  })
}


export function roleDeleteById(data){
  return request({ //删除角色
    url:'/role/delete',
    method:'post',
    data
  })
}

export function roleFindByRoleMenu(data){
  return request({ //查询角色菜单
    url:'/resource/findByRoleMenu',
    method:'post',
    data
  })
}

export function findByRoleButton(data){
  return request({
    url:'/resource/findByRoleButton',
    method:'post',
    data
  })
}

export function roleFindByRoleMenuTree(data){
  return request({ //查询角色菜单树
    url:'/resource/findByRoleMenuTree',
    method:'post',
    data
  })
}

export function menuList(data){
  return request({ //查询菜单树
    url:'/resource/menuList',
    method:'post',
    data
  })
}

export function deleteResource(data){
  return request({ //删除资源
    url:'/resource/delete',
    method:'post',
    data
  })
}

export function updateResource(data){
  return request({ //修改资源
    url:'/resource/update',
    method:'post',
    data
  })
}

export function saveResource(data){
  return request({ //保存资源
    url:'/resource/save',
    method:'post',
    data
  })
}

export function getResBtnList(data){
  return request({ //获取菜单下所有按钮
    url:'/resource/buttonList',
    method:'post',
    data
  })
}

export function saveRoleResource(data){
  return request({ //获取菜单下所有按钮
    url:'/role/saveRoleResource',
    method:'post',
    data
  })
}

export function findByRoleButtonTree(data){
  return request({ //获取菜单下所有按钮树列表
    url:'/resource/findByRoleButtonTree',
    method:'post',
    data
  })
}
