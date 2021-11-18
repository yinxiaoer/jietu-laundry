// pages/user/userinfo/username/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    name:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    changeName(el) {
      this.setData({ name: el.detail.value, })
    },
    submit(){
      console.log(this.data.name)
    }
  }
})
