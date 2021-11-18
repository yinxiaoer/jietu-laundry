// components/merchantCard/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    item: { // card 数据
      type: Object,
      value: {},
    },
    itemType: {
      type: String,
      value: 'home', // home history collection
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    clickEvent() {
      this.triggerEvent('click', this.data.item)
    },
    cancelCollect() {
      this.triggerEvent('cancelCollect', this.data.item)
    },
  }
})
