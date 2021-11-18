Component({
  /**
   * 组件的属性列表
   */
  properties: {
    show: {
      type: Boolean,
      value: false,
    },
    title: {
      type: String,
      value: '选择收获地址'
    },
    addresses: {
      type: Array,
    },
  },
  data: {},
  methods: {
    onClose() {
      this.triggerEvent('close')
    },
    updateAddress({detail}) {
      this.triggerEvent('update', detail.value)
    },
    addAddress() {
      this.triggerEvent('add')
    },
  }
})
