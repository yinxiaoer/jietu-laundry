// pages/user/address/update/index.js
import {$wuxSelect} from "../../../../packages/@wuxui/index"
const { fly, dotProp, setAddress, globalData } = getApp()
const { findIndex, find, get } = getApp().lodash
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultFieldNames: {
      label: 'name',
      value: 'code',
      children: 'children'
    },
    updateId: null,
    value: [], // 省市区数组id
    title: '', // 省市区中文
    isDefault: '是', // 是否为默认地址
    addressOptions: {
      options: []
    },
    addressInfo: {
      receiverName: '',
      receiverMobile: '',
      defaulted: true,
      latitude: '',
      longitude: '',
      locationAddress: '',
      detailsAddress: '',
      locationName: '',
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getLocale()
    if(get(options, 'id')) {
      wx.setNavigationBarTitle({
        title: '修改地址'
      })
      const updateAddress = find(globalData.addresses, {id: Number(options.id)})
      this.setData({
        updateId: get(options, 'id'),
        addressInfo: {
          receiverName: updateAddress.receiverName,
          locationAddress: updateAddress.locationAddress,
          // locationName: updateAddress.locationName,
          latitude: updateAddress.latitude,
          longitude: updateAddress.longitude,
          detailsAddress: updateAddress.detailsAddress,
          receiverMobile: updateAddress.receiverMobile,
          defaulted: true,
        },
      })
    }else {
      wx.setNavigationBarTitle({
        title: '新增地址'
      })
      this.setData({
        updateId: null
      })
    }
  },
  getLocale() {
    this.setData({
      longitude: wx.getStorageSync('longitude'),
      latitude: wx.getStorageSync('latitude'),
    })
  },
  onOpen() {
    this.setData({ visible: true })
  },
  onClose() {
    this.setData({ visible: false })
  },
  onChangeAddress(e) {
    this.setData({ value: e.detail.value,
      title: e.detail.options.map((n) => n.name).join('/') })
    this.setData({
      addressInfo: {
        ...this.data.addressInfo,
        provinceId: get(this.data.value, '0', null),
        cityId: get(this.data.value, '1', null),
        districtId: get(this.data.value, '2', null),
      },
    })

  },
  onLoadOptions(e) {
    function findOptionCodeIndex(options, values) {
      if(values.length===1){
        const provinceIndex = findIndex(options, {code: values[values.length-1]})
        return [provinceIndex]
      }
      if(values.length===2){
        const provinceIndex = findIndex(options, {code: values[0]})
        const cityIndex = findIndex(options[provinceIndex].children, {code: values[1]})
        return [provinceIndex, cityIndex]
      }
    }
    const indexs = findOptionCodeIndex(this.data.addressOptions,  e.detail.value)
    const getCity = (code)=> {
      fly.post('/area/getByParent', {
        parent: code
      }).then(res => {
        const index = indexs[0]
        const newOptions = dotProp.set(e.detail.options, '0.children', res.data.area.map(area => ({
          ...area,
          isLeaf: false,
          children: []
        })))
        const addressOptions = dotProp.set(this.data.addressOptions, `${index}`, newOptions[0])
        this.setData({
          addressOptions,
        })
      })
    }
    const getDistrict = (code) => {
      fly.post('/area/getByParent', {
        parent: code
      }).then(res => {
        const index = indexs[0]
        const cityIndex = indexs[1]
        const newOptions = dotProp.set(e.detail.options, `0.children.${cityIndex}.children`, res.data.area.map(area => ({
          ...area,
          isLeaf: true,
          children: null
        })))
        const addressOptions = dotProp.set(this.data.addressOptions, `${index}`, newOptions[0])
        this.setData({
          addressOptions,
        })
      })
    }
    if(indexs.length===1 && e.detail.options[0].children.length===0){
      getCity(e.detail.value[0])
    }else if(indexs.length===2&& e.detail.options[0].children[indexs[1]].children.length===0) {
      getDistrict(e.detail.value[1])
    }
  },
  updateName(e) {
    this.setData({
      addressInfo: {
        ...this.data.addressInfo,
        receiverName: e.detail.value,
      },
    })
  },
  updateAddress(e) {
    this.setData({
      addressInfo: {
        ...this.data.addressInfo,
        detailsAddress: e.detail.value,
      },
    })
  },
  updateMobile(e) {
    this.setData({
      addressInfo: {
        ...this.data.addressInfo,
        receiverMobile: e.detail.value,
      },
    })
  },
  changeDefault() {
    $wuxSelect('#wux-select').open({
      value: this.data.isDefault,
      options: [
        '是',
        '否',
      ],
      onConfirm: (value, index, options) => {
        if (index !== -1) {
          this.setData({
            isDefault: value,
            addressInfo: {
              ...this.data.addressInfo,
              defaulted: value==='是' ? true : false
            },
          })
        }
      },
    })
  },
  openMap() {
    const _this = this;
    wx.getSetting({
      success(local) {
        if (local.authSetting['scope.userLocation']) {
          _this.openChooseLocation()
        } else {
          wx.showModal({
            content: '地理位置选择需要获取您的地理位置',
            confirmText: '去开启',
            success({confirm}) {
              if(confirm) {
                wx.openSetting({
                  success(res) {
                    if(res.authSetting['scope.userLocation']) {
                      _this.openChooseLocation()
                    }
                  },
                })
              }
            },
          })
        }
      }
    })
  },
  openChooseLocation() {
    const _this = this
    wx.chooseLocation({
      success(res){
        _this.setData({
          addressInfo: {
            ..._this.data.addressInfo,
            locationAddress: res.address,
            locationName: res.name,
            latitude: res.latitude,
            longitude: res.longitude,
          }
        })
      }
    })
  },
  save(){
    fly.post('/customer/saveOrUpdateAddress', {
      ...this.data.addressInfo,
      id: this.data.updateId
    }).then(res => {
      setAddress(res.data.addresses)
      wx.navigateBack({
        delta: 1,
      })
    })
  },
})
