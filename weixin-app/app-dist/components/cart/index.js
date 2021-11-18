const { map } = getApp().lodash

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    shops: {
      type: Array,
      value: [],
      observer() {
        this.setData({
          number: this.getNumber(),
        })
      }
    },
    shoppingCartAmount: {
      type: Number,
    },
  },
  /**
   * 组件的初始数据
   */
  data: {
    openedCon: false,
  },
  /**
   * 组件的方法列表
   */
  methods: {
    closeDetail() {
      this.setData({
        openedCon: false,
      })
    },
    buy() {
      if(this.data.shops.length===0){
        return false
      }
      this.triggerEvent('buy', this.data.shops)
    },
    getNumber() {
      return this.data.shops.reduce((sum, shop)=>{
        return sum + shop.number
      }, 0)
    },
    getTotalPrice() {
      return this.data.shops.reduce((sum, shop)=>{
        return sum + shop.price
      }, 0)
    },
    clearCart() {
      this.setData({
        openedCon: !this.data.openedCon
      })
      this.triggerEvent('clear')
    },
    changeOpen() {
      this.setData({
        openedCon: !this.data.openedCon
      })
    },
    reduce(val) {
      const item = {
        product: {
          ...val.target.dataset.item,
          id: val.target.dataset.item.goodsId
        },
        number: val.target.dataset.item.number - 1,
      }
      this.triggerEvent('update', item)
    },
    add(val) {
      const item = {
        product: {
          ...val.target.dataset.item,
          id: val.target.dataset.item.goodsId
        },
        number: val.target.dataset.item.number + 1,
      }
      this.triggerEvent('update', item)
    },
  }
})
