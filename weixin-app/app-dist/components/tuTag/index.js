// components/tuTag/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    controlled: {
      type: Boolean,
      value: false,
    },
    dataSet: {
      type: String | Number | Array | Boolean | Object,
      value: '',
    },
    active: {
      type: Boolean,
      value: false,
      observer(newValue) {
        if(this.data.controlled) {
          this.setData({
            isActive: newValue,
          })
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    isActive: false,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    change() {
      if(!this.data.controlled) {
        this.setData({
          isActive: !this.data.isActive
        })
      }
      this.triggerEvent('click', this.data.dataSet)
      this.triggerEvent('change', {
        active: this.data.isActive,
        dataSet: this.data.dataSet,
      })
    },
  }
})
