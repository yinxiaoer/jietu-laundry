<template>
  <div class="login-container">
    <div class="center">
      <div class="left">
        <p class="welcome">Welcome</p>
        <p class="system-name">洁兔后台管理系统</p>
      </div>
      <div class="right">
        <div class="form">
            <el-form class="card-box login-form" autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left">
              <el-form-item prop="username">
                <span class="svg-container svg-container_login">
                  <svg-icon icon-class="user" />
                </span>
                <el-input name="username" size="mini" type="text" v-model="loginForm.userName" autoComplete="on" placeholder="用户名" />
              </el-form-item>

              <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password" />
                </span>
                <el-input name="password" size="mini"  :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password" autoComplete="on"
                  placeholder="密码" />
                <span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>
              </el-form-item>
              <el-row>
                <el-col :span="24">
                   <el-checkbox v-model="remeberPwd">记住密码</el-checkbox>
                </el-col>
                <el-col :span="24">
                  <el-button type="primary" style="margin-top:50px;width:100%;border-radius:30px;" :loading="loading" @click.native.prevent="handleLogin">登录</el-button>
                </el-col>
              </el-row>
            </el-form>
          </div>
      </div>
    </div>
  </div>
</template>
<script>
import { isvalidUsername } from '@/utils/validate'
import {loginByUsername}  from '../../api/login'
import { param2Obj } from '@/utils'
import 'element-ui/lib/theme-chalk/display.css';
export default {
  name: 'login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 3) {
        callback(new Error('密码不能小于6位'))
      } else {
        callback()
      }
    }
    return {
      remeberPwd:false,
      loginForm: {
        userName: '',
        password: ''
      },
      loginRules: {
        userName: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      pwdType: 'password',
      loading: false,
      showDialog: false
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.loading = true;
       this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('LoginByUsername', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: '/' })
            // this.showDialog = true
          }).catch(() => {
            this.loading = false
          })
        } else {
          this.loading = false
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  created() {
    // window.addEventListener('hashchange', this.afterQRScan)
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan)
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .login-container{
    height:100vh;
    background:url(../../assets/index_images/login.png) no-repeat;
    background-size:cover;
    background-position:center center;
    position:relative;
    .center{
      position:absolute;
      top:50%;
      left:50%;
      margin-left:-450px;
      margin-top:-225px;
      width:900px;
      height:450px;
       border-box:border-sizing;
      .left{
        float:left;
        width:50%;
        height:100%;
        border-box:border-sizing;
        background:rgba(255,255,255,.1);
        position:relative;
        .welcome{
          color:#fff;
          font-family:'微软雅黑';
          font-size:22px;
          top:100px;
          left:100px;
          text-align:center;
          margin-top:120px;
        }
        .system-name{
          color:#fff;
          font-family:'微软雅黑';
          font-size:24px;
          text-align:center;
           margin-top:20;
        }
      }
      .right{
        float:left;
        width:50%;
        height:100%;
         border-box:border-sizing;
        background:rgba(255,255,255,.5);
      .form{
          input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #293444 inset !important;
            -webkit-text-fill-color: #fff !important;
          }
          input {
            background-color:transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            height: 30px;
            color:#eee;
          }
          .el-input {
            display: inline-block;
            height: 30px;
            width: 80%;
            background-color:transparent;
          }
          .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
          }
          .svg-container {
            padding: 6px 5px 6px 15px;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
            &_login {
              font-size: 20px;
            }
          }
          .title {
            font-size: 26px;
            font-weight: 400;
            padding: 25px 0px;
            margin:0px;
            text-align: center;
            font-weight: bold;
          }
          .login-form {
              margin-top:100px;
             padding: 0 35px;
          }
          .el-form-item {
            border-bottom: 1px solid #eee;
            border-radius: 5px;
            color: #eee;
          }
          .el-input__inner{
            color:#eee;
            background-color:transparent;
          }
          .el-form-item__content{
            line-height:30px;
          }
          .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            cursor: pointer;
            user-select:none;
          }
          .thirdparty-button{
            position: absolute;
            right: 35px;
            bottom: 28px;
          }
       }
      }
    }
  }

    .el-checkbox{
      color:#eee;
    }
    .el-checkbox__input.is-checked+.el-checkbox__label{
      color:#eee;
    }
</style>
