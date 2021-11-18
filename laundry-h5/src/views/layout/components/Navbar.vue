<template>
  <div>
    <el-menu class="navbar" mode="horizontal">
      <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>

      <breadcrumb class="breadcrumb-container"></breadcrumb>

      <div class="right-menu">
        <span class="user">当前用户：{{userInfo.username}}_{{userInfo.realname}}</span>
        <el-tooltip effect="dark" content="全屏" placement="bottom">
          <screenfull class="screenfull right-menu-item"></screenfull>
        </el-tooltip>
        <el-dropdown class="avatar-container right-menu-item" trigger="click">
          <div class="avatar-wrapper">
            <img class="user-avatar" src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80">
            <i class="el-icon-caret-bottom"></i>
          </div>
          <el-dropdown-menu slot="dropdown">
            <router-link to="/">
              <el-dropdown-item>
                首页
              </el-dropdown-item>
            </router-link>
            <el-dropdown-item divided>
              <span @click="viewUserInfo" style="display:block;">个人资料</span>
            </el-dropdown-item>
            <el-dropdown-item divided>
              <span @click="handleResPwd" style="display:block;">修改密码</span>
            </el-dropdown-item>
            <el-dropdown-item divided>
              <span @click="logout" style="display:block;">退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-menu>
    <el-dialog title="个人资料"
               :visible.sync="userInfoDialogVisible"
               :close-on-click-modal="false">
      <el-form :model="userInfo">
        <el-form-item label="用户名" label-width="120px" required>
          <el-input size="mini" v-model="userInfo.username"  :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" required>
          <el-input size="mini" v-model="userInfo.realname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" required>
          <el-input size="mini" v-model="userInfo.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px">
          <el-input size="mini" v-model="userInfo.email" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userInfoDialogVisible = false">取 消</el-button>

      </div>
    </el-dialog>
    <el-dialog title="修改密码"
               :visible.sync="dialogResetPassVisible"
               :close-on-click-modal="false">
      <el-form :model="resetPsdForm">
        <el-form-item label="原密码" label-width="120px">
          <el-input size="mini" type="password" v-model="resetPsdForm.oldPassword" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" label-width="120px">
          <el-input size="mini" type="password" v-model="resetPsdForm.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="重新输入密码" label-width="120px">
          <el-input size="mini" type="password" v-model="resetPsdForm.relPassword" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleResPwd()"  size="mini">取 消</el-button>
        <el-button type="primary" @click="saveResPwd()"  size="mini">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import Breadcrumb from '@/components/Breadcrumb'
  import Hamburger from '@/components/Hamburger'
  import Screenfull from '@/components/Screenfull'
  import errLogStore from 'store/errLog'
  import { ajax } from '@/api/ajax'
  import { Message } from 'element-ui'

  export default {
    components: {
      Breadcrumb,
      Hamburger,
      Screenfull
    },
    data() {
      return {
        log: errLogStore.state.errLog,
        userInfoDialogVisible: false,
        dialogResetPassVisible: false, // 是否显示重置密码弹窗,
        userInfo: {// 编辑form
          username: '',
          realname: '',
          phone: '',
          email: '',
          id: ''
        },
        resetPsdForm: { // 重置密码form
          userId: '',
          oldPassword: '', // 旧密码
          password: '', // 新密码
          relPassword: '' // 重新输入的密码
        }
      }
    },
    created() {
      ajax('getCurrentUser').then(response => {
        var user = response.data.obj
        this.userInfo.username = user.username
        this.userInfo.realname = user.realname
        this.userInfo.phone = user.phone
        this.userInfo.email = user.email
        this.resetPsdForm.userId = user.id
      })
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'name',
        'avatar',
        'language'
      ])
    },
    methods: {
      toggleSideBar() {
        this.$store.dispatch('toggleSideBar')
      },
      logout() {
        this.$router.push('/login')
        this.$store.dispatch('LogOut').then(() => {
          location.reload()// 为了重新实例化vue-router对象 避免bug
        })
      },
      viewUserInfo() { // 查看个人资料
        this.userInfoDialogVisible = true
      },
      saveUserInfo() {
        this.userInfoDialogVisible = false
        ajax('user/updatePassword', this.userInfo).then(response => {
          this.userInfo.username = null
          this.userInfo.realname = null
          this.userInfo.phone = null
          this.userInfo.email = null
          this.userInfo.id = null
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          })
          setTimeout(() => {
            this.logout()
          }, 1000)
        }).catch(reason => {
          this.userInfoDialogVisible = true
        })
      },
      handleResPwd() { // 显示修改密码框
        this.dialogResetPassVisible = true
      },
      cancleResPwd() {

        this.resetPsdForm.oldPassword = ''
        this.resetPsdForm.password = ''
        this.resetPsdForm.relPassword = ''
        this.dialogResetPassVisible = false
      },
      saveResPwd() { // 修改密码
        if(this.resetPsdForm.password!=this.resetPsdForm.relPassword){
          Message.error('两次输入密码不一致');
          return ;
        }
        var dataForm = this.resetPsdForm // 设置请求dataForm
        ajax('user/updatePassword', dataForm).then(response => {
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          })
          this.dialogResetPassVisible = false
          this.resetPsdForm.userId = ''
          this.resetPsdForm.oldPassword = ''
          this.resetPsdForm.password = ''
          this.resetPsdForm.relPassword = ''
          setTimeout(() => {
            this.logout()
          }, 1000)
        }).catch(reason => {
          this.dialogResetPassVisible = true
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .navbar {
    height: 50px;
    line-height: 50px;
    border-radius: 0 !important;

    .hamburger-container {
      line-height: 58px;
      height: 50px;
      float: left;
      padding: 0 10px;
    }

    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }

    .right-menu {
      float: right;
      height: 100%;

      &:focus {
        outline: none;
      }

      .user {
        position: absolute;
        right: 133px;
      }

      .right-menu-item {
        display: inline-block;
        margin: 0 8px;
      }

      .screenfull {
        height: 20px;
      }

      .international {
        vertical-align: top;

        .international-icon {
          font-size: 20px;
          cursor: pointer;
          vertical-align: -5px;
        }
      }

      .theme-switch {
        vertical-align: 15px;
      }

      .avatar-container {
        height: 50px;
        margin-right: 30px;

        .avatar-wrapper {
          cursor: pointer;
          margin-top: 5px;
          position: relative;

          .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>
