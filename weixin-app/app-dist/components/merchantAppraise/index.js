// components/merchantAppraise/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    appraiselist: {
      type: Object,
      value: {},
    },
    commentPagination: {
        type: Object,
        value: {
            hasNextPage: true,
        },
    },
    commentCount: {
      type: Object,
      value: {
        goodComment: 0,
        negativeComment: 0,
        imageComment: 0,
        satisficing: 0,
        totalCount: 0,
      },
      observer({goodComment, negativeComment, imageComment}) {
        this.setData({
          tags: [
            {
              name: '全部',
              id: '1',
              count:'all',
              type:'all'
            },
            {
              name: '好评',
              id: '2',
              count: goodComment || 0,
              type:'good'
            },
            {
              name: '差评',
              id: '3',
              count: negativeComment || 0,
              type:'bad'
            },
            {
              name: '有图评价',
              id: '4',
              count: imageComment || 0,
              type:'picture'
            },
          ],
        })
      },
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    tags:[
      {
        name: '全部',
        id: '1',
        count:'all',
        type:'all'
      },
      {
        name: '好评',
        id: '2',
        count: '121',
        type:'good'
      },
      {
        name: '差评',
        id: '3',
        count: '2',
        type:'bad'
      },
      {
        name: '有图评价',
        id: '4',
        count: '32',
        type:'picture'
      },
    ],
    selected:'1'
  },

  /**
   * 组件的方法列表
   */
  methods: {
    selectedTag({ currentTarget}){
      this.setData({
        selected: currentTarget.dataset.id
      })
      // this.triggerEvent()
    }
  }
})
