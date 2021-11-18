const moment = require('../../utils/comment');
import map from '../../utils/lodash/map'
import find from '../../utils/lodash/find'

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
      value: '选择取件时间'
    },
    startDate: {
      type: String,
      observer(startDate) {
        this.init(startDate.replace(/\-/g, '/'))
      }
    },
    startTime: {
      type: String,
    },
    showZH: {
      type: Boolean,
      value: true,
    },
    step: {
      type: Number,
      value: 2,
    },
    timeList: {
      type: Array,
      value: [],
      observer(times) {
        this.setData({
          times: times.map(time => ({
            ...time,
            disabled: true,
          }))
        })
        this.init(this.data.startDate.replace(/\-/g, '/'))
      },
    },
  },
  /**
   * 组件的初始数据
   */
  data: {
    selectedDate: '',
    selectedTime: null,
    dates: [],
    times: [],
  },

  /**
   * 组件的方法列表
   */
  methods: {
    selectedTime({currentTarget}) {
      if(currentTarget.dataset.item.disabled) {
        return false
      }
      this.setData({
        selectedTime: currentTarget.dataset.item
      })
      this.onClose()
    },
    selectedDate({detail}) {
      this.setData({
        selectedDate: detail.key
      })
      const newTimes =  map(this.data.times, time => ({
        ...time,
        disabled: this.isDisabled(time.start)
      }))
      if(find(newTimes, time => !time.disabled)) {
        this.setData({
          selectedTime: find(newTimes, time => !time.disabled),
        })
      }else {
        this.setData({
          selectedTime: null,
        })
      }
      this.setData({
        times: newTimes,
      })
    },
    onClose() {
      let dateRange = {
        startDate: null,
        endDate: null,
      }
      if(this.data.selectedTime) {
         dateRange = {
          startDate: `${this.data.selectedDate} ${this.data.selectedTime.start}`,
          endDate: `${this.data.selectedDate} ${this.data.selectedTime.end}`
        }
        this.triggerEvent('close', dateRange)
      }
    },
    isDisabled(time) {
      const itemDate = `${this.data.selectedDate} ${time}`
      return new Date(itemDate.replace(/-/g, "/")).getTime()<= new Date().getTime()
    },
    init(startDate) {
      if(this.data.times.length===0) {
        return false
      }
      let dates = []
      for(var i =0; i< 7; i ++) {
        const addDate = moment(new Date(startDate)).add(i, 'd');
        if(this.data.showZH && i===0) {
          dates.push({
            value: addDate.format('YYYY-MM-DD'),
            label: `${addDate.format('今天')}(${addDate.format('ddd')})`
          })
        }
        if(this.data.showZH && i===1) {
          dates.push({
            value: addDate.format('YYYY-MM-DD'),
            label: `${addDate.format('明天')}(${addDate.format('ddd')})`
          })
        }
        if(!this.data.showZH || i>=2) {
          dates.push({
            value: addDate.format('YYYY-MM-DD'),
            label: addDate.format('M月DD日')
          })
        }
      }
      this.setData({
        dates,
        selectedDate: dates[0].value,
      })
      const newTimes =  map(this.data.times, time => ({
        ...time,
        disabled: this.isDisabled(time.start)
      }))
      if(find(newTimes, time => !time.disabled)) {
        this.setData({
          selectedTime: find(newTimes, time => !time.disabled) ,
        })
      }else {
        this.setData({
          selectedTime: null,
        })
      }
      this.setData({
        times: newTimes,
      })
    },
  }
})
