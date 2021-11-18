const { get, find } = getApp().lodash
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    cartData: {
      type: Object,
      value: {},
      observer({cart, product}) {
        const productCart = find(cart, {goodsId: product.id})
        this.setData({
          product,
          visibleNumber:  !!productCart && get(productCart, 'number', 0)>0,
          number: get(productCart, 'number', 0),
        })
      }
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    get,
    visibleDetail: false,
    product: [],
    number: 0,
    visibleNumber: false,
  },
  /**
   * 组件的方法列表
   */
  methods: {
    showDetail() {
      this.triggerEvent('showDetail', {product: this.data.product})
    },
    reduce() {
      const number =  this.data.number-1
      this.setData({
        number: number < 0 ? 0 : number,
      })
      this.triggerEvent('update', {product: this.data.product,number})
    },
    add() {
      const number =  this.data.number + 1
      this.setData({
        visibleDetail: false,
        number,
      })
      if(number===1){
        this.triggerEvent('add', {product: this.data.product,number})
      } else {
        this.triggerEvent('update', {product: this.data.product,number})
      }
    },
  }
})
