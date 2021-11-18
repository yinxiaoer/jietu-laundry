const { isEmpty } = getApp().lodash

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    thumb: {//缩略图
      type: String,
      value: '',
    },
    icon:{//小图标
      type:String,
      value:'',
    },
    title: {//左侧文字
      type: String,
      value: '',
    },
    extra: {//右侧文字
      type: String,
      value: '',
    },
    isLink: {//右侧指向图标
      type: Boolean,
      value: false,
    },
    isBorder:{
      type: Boolean,
      value: false,
    },
    isTop:{
      type: Boolean,
      value: false,
    },
  },

  /**
   * 组件的方法列表
   */
  methods: {
    change(){
      this.triggerEvent('changeItem')
    }
  }
})
