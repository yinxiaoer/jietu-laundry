import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV);

Vue.use(Router);

/* Layout */
import Layout from '../views/layout/Layout'
import svgIcons from '../views/svg-icons/index'
import dashboard from '../views/dashboard/index'
import user from '../views/system/user'
import role from '../views/system/role'
import resource from '../views/system/resource'
import systemParameter from '../views/system/systemParameter'
import systemParameterType from '../views/system/systemParameterType'
import goodsList from '../views/goods/goodsList'
import goodsCategory from '../views/goods/goodsCategory'
import goodsDetails from '../views/goods/goodsDetails'
import goodsEdit from '../views/goods/goodsEdit'
import merchantList from '../views/merchant/merchant'
import merchantDetails from '../views/merchant/merchantDetails'
import orderList from '../views/order/orderList'
import orderDetails from '../views/order/orderDetails'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    role: ['backend','editor']     will control the page role (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if fasle ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index') },
  { path: '/authredirect', component: _import('login/authredirect') },
  { path: '/404', component: _import('errorPage/404') },
  { path: '/401', component: _import('errorPage/401') },
    {
    path: '', // 路由地址
    component: Layout, // 所加载的组件或组件地址
    redirect: 'dashboard', // 是否重定向
    children: [{ //子组件
      path: 'dashboard',
      component: dashboard, //这个就返回组件的名称
      name: 'dashboard', //组件名称
      meta: { title: '首页'} //组件meta信息
    }]
  }
];

const router=new Router({
   mode: 'hash', //后端支持可开
  // scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
});
export const asyncRouterMap=[
  {
    path: '', // 路由地址
    component: Layout, // 所加载的组件或组件地址
    redirect: 'dashboard', // 是否重定向
    children: [{ //子组件
      path: 'dashboard',
      component: dashboard, //这个就返回组件的名称
      name: 'dashboard', //组件名称
      meta: { title: '首页'} //组件meta信息
    }]
  },
  {
    path: '/icons',
    component: Layout,
    name: 'icons',
    redirect:'icon',
    meta: {title: '图标'},
    children: [{
      path: 'icon', component: svgIcons, name: 'icons', meta: { title: '图标'}
    }]
  },
  {
    path: '/goods',
    component: Layout,
    name: 'goods',
    meta: {title: '商品管理'},
    children: [
      { path: 'goodsList', component:goodsList, name: 'goodsList', meta: { title: '商品列表'}},
      { path: 'goodsCategory', component:goodsCategory, name: 'goodsCategory', meta: { title: '商品类型'}},
      { path: 'goodsDetails/:id', component:goodsDetails, name: 'goodsDetails', meta: { title: '商品详情'}},
      { path: 'goodsEdit/:id', component:goodsEdit, name: 'goodsEdit', meta: { title: '保存商品'}},
    ]
  },
  {
    path: '/merchant',
    component: Layout,
    name: 'merchant',
    meta: {title: '商户管理'},
    children: [
      { path: 'merchantList', component:merchantList, name: 'merchantList', meta: { title: '商户列表'}},
      { path: 'merchantDetails/:id', component:merchantDetails, name: 'merchantDetails', meta: { title: '商户详情'}},
    ]
  },
  {
    path: '/order',
    component: Layout,
    name: 'order',
    meta: {title: '订单管理'},
    children: [
      { path: 'orderList', component:orderList, name: 'orderList', meta: { title: '订单列表'}},
      { path: 'orderDetails/:id', component:orderDetails, name: 'orderDetails', meta: { title: '订单详情'}},
    ]
  },
  {
    path: '/system',
    component: Layout,
    name: 'system',
    meta: {title: '系统管理'},
    children: [
      { path: 'user', component:user, name: 'user', meta: { title: '用户管理'}},
      { path: 'role', component: role, name: 'role', meta: { title: '角色管理'}},
      { path: 'resource', component:resource, name: 'resource', meta: { title: '用户资源管理' }},
      { path: 'systemParameter', component: systemParameter, name: 'systemParameter', meta: { title: '系统参数配置'}},
      { path: 'systemParameterType', component: systemParameterType, name: 'systemParameterType', meta: { title: '系统参数类型'}},
    ]
  },
  { path: '*', redirect: '/404' }
]
export default  router
