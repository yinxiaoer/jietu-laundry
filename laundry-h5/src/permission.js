import router from './router'
import {asyncRouterMap,constantRouterMap} from '@/router'
import {getToken} from '@/utils/auth'
 //router.addRoutes(asyncRouterMap || []) // 动态添加可访问路由表
//import asyncRouter from './router/asyncRouter.js'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'


function hasPermission(route, menus) {
 return true
}
function filterAsyncRouter(asyncRouterMap, menus) {
  if (menus == null) {
    return null
  }
  const accessedRouters = asyncRouterMap.filter(route => {
    if (hasPermission(route, menus)) {
      if (route.children && route.children.length > 0) {
        route.children = filterAsyncRouter(route.children, menus)
      }
      return true
    }
    return false
  })
  return accessedRouters
}
let isAddRouter=false;//是否添加路由，防止多次添加路由
if(sessionStorage.getItem('isLogin')){
  if(!isAddRouter){
    router.addRoutes(asyncRouterMap || []);
    isAddRouter=true;
  }
}
const whiteList = ['/login', '/401', '/404'];// 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start();// 开启Progress
  if(sessionStorage.getItem('isLogin')){
    if (to.path === '/login') {
      next( '/' );
    }else{
      if(!isAddRouter){
        store.dispatch('GetUserInfo').then(res => {
           isAddRouter=true;
             //获得服务器端路由
             //解析路由
             //.............
             //添加路由
             router.addRoutes(asyncRouterMap || []);
             next()
        }).catch(() => {
          store.dispatch('FedLogOut').then(() => {
            Message.error('验证失败,请重新登录');
            next({ path: '/login' });
          })
        })
      }else{
        next()
      }
    }
  }else{
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next();
      NProgress.done()
    } else {
      next({path:'/login'}); // 否则全部重定向到登录页
      NProgress.done()
    }
  }
});

router.afterEach(() => {
  NProgress.done() // 结束Progress
});
