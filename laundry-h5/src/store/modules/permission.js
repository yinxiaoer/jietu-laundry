//import { asyncRouterMap, constantRouterMap } from '@/router'



/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param roles
 */
// function filterAsyncRouter(asyncRouterMap, roles) {
//   const accessedRouters = asyncRouterMap.filter(route => {
//     if (hasPermission(roles, route)) {
//       if (route.children && route.children.length) {
//         route.children = filterAsyncRouter(route.children, roles)
//       }
//       return true
//     }
//     return false
//   })
//   return accessedRouters
// }

// const permission = {
//   state: {
//     routers: constantRouterMap,
//     addRouters: []
//   },
//   mutations: {
//     SET_ROUTERS: (state, routers) => {
//       state.addRouters = routers
//       state.routers = constantRouterMap.concat(routers)
//     }
//   },
//   actions: {
//     GenerateRoutes({ commit }, data) {
//       return new Promise(resolve => {
//         const { roles } = data
//         let accessedRouters
//         if (roles.indexOf('ADMIN') >= 0) {
//           accessedRouters = asyncRouterMap
//         } else {
//           accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
//         }
//         commit('SET_ROUTERS', accessedRouters)
//         resolve()
//       })
//     }
//   }
// }

// export default permission
