y<template>
  <div class="menu-wrapper">
    <template v-for="item in route">
     <router-link v-if="item.children&&item.children.length===0" :to="item.path" :key="item.path">
        <el-menu-item :index="item.path" class='submenu-title-noDropdown'>
          <svg-icon v-if="item.meta&&item.meta.icon" :icon-class="item.meta.icon"></svg-icon>
          <span v-if="item.meta&&item.meta.title">{{generateTitle(item.meta.title)}}</span>
        </el-menu-item>
      </router-link>
      <router-link v-if="item.children&&item.children.length===1" :to="item.path+'/'+item.children[0].path" :key="item.children[0].name">
        <el-menu-item :index="item.path+'/'+item.children[0].path" class='submenu-title-noDropdown'>
          <svg-icon v-if="item.children[0].meta&&item.children[0].meta.icon" :icon-class="item.children[0].meta.icon"></svg-icon>
          <span v-if="item.children[0].meta&&item.children[0].meta.title">{{generateTitle(item.children[0].meta.title)}}</span>
        </el-menu-item>
      </router-link>
     <el-submenu v-if="item.children&&item.children.length>1" :index="item.name||item.path" :key="item.path">
        <template slot="title">
          <svg-icon v-if="item.meta&&item.meta.icon" :icon-class="item.meta.icon"></svg-icon>
          <span v-if="item.meta&&item.meta.title">{{generateTitle(item.meta.title)}}</span>
        </template>
        <template v-for="child in item.children">
          <sidebar-item class="nest-menu" v-if="child.children&&child.children.length>0" :routes="[child]" :key="child.path"></sidebar-item>

          <router-link v-else :to="item.path+'/'+child.path" :key="child.name">
            <el-menu-item :index="item.path+'/'+child.path">
              <svg-icon v-if="child.meta&&child.meta.icon" :icon-class="child.meta.icon"></svg-icon>
              <span v-if="child.meta&&child.meta.title">{{generateTitle(child.meta.title)}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>
    </template>
  </div>
</template>
<script>
import getUserRoleMenu from '@/api/getUserRoleMenu'
export default {
  name: 'SidebarItem',
  data() {
    return {
      route:[]
    }
  },
  created(){
    // 获取侧边栏列表数据
    getUserRoleMenu().then(response=>{
      this.route=response.data.obj;
    })
  },
  methods: {
    generateTitle(title) {
      return title;
    }
  }
}
</script>

