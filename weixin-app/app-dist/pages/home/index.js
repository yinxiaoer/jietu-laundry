
const {globalData, fly, getAddress} = getApp()
const {map} = getApp().lodash
import regeneratorRuntime from '../../utils/regenerator-runtime/runtime'

exports.default = Page({
    data: {
        actives: [{ id: 1,
            image: {
                url: '/banner1.png'
            }
        }, { id: 2,
            image: {
                url: '/banner2.png'
            }
        }, {
            id: 3,
            image: {
                url: '/banner3.png'
            }
        },
        ],
        swiperConfig: {
            indicatorDots: true,
            autoplay: true,
            interval: 3000,
            duration: 300,
            circular: true,
            height: `${globalData.system.width/2}`,
        },
        merchants: [],
        pagination: {
            page: 1,
            limit: 20,
            hasNextPage: true,
        },
    },
    onLoad() {
        this.getMerchants(this.data.pagination)
        getAddress()
    },
    onShow() {
        wx.setNavigationBarColor({
            backgroundColor: '#FFFFFF',
            frontColor: '#000000'
        })
    },
    getMerchants( pagination ) {
        const _this = this
        wx.getLocation({
            altitude: true,
            type: 'gcj02',
            async success({latitude, longitude}) {
                wx.setStorageSync('latitude', latitude)
                wx.setStorageSync('longitude', longitude)
                  _this.getList({
                      latitude,
                      longitude,
                      pagination,
                  })
            }
        })
    },
    goToMerchant({detail}) {
        wx.navigateTo({
            url: `merchant/index?id=${detail.id}`
        })
    },
    getDistance(distance) {
        return distance<1000?`${distance}m`: `${(distance/1000).toFixed(1)}km`
    },
    async getList({latitude, longitude, pagination}) {
        const { hasNextPage, page, limit } = pagination
        if(!hasNextPage && page != 1) {
            return false
        }
        const _this = this
        wx.getSetting({
            async success(local) {
                if(local.authSetting['scope.userLocation']) {
                    try {
                        const res = await fly.post('merchant/findCircumMerchant/', {
                            latitude,
                            longitude,
                            page,
                            limit,
                        })
                        const merchants = map(res.data.merchant, merchant => (
                            {
                                ...merchant,
                                distance: _this.getDistance(merchant.distance)
                            }
                        ))
                        _this.setData({
                            merchants: page == 1 ? merchants : _this.data.merchants.concat(merchants),
                            pagination: {
                                ..._this.data.pagination,
                                page,
                                hasNextPage: merchants.length == limit,
                            },
                        })
                        wx.stopPullDownRefresh()
                    }catch (e) {
                        wx.stopPullDownRefresh()
                    }
                } else {
                    wx.stopPullDownRefresh()
                    wx.showModal({
                        content: '衣物取送需要您的地理位置',
                        confirmText: '去开启',
                        success({confirm}) {
                            if(confirm) {
                                wx.openSetting({
                                    success(res) {
                                        if(res.authSetting['scope.userLocation']) {
                                            wx.getLocation({
                                                altitude: true,
                                                type: 'gcj02',
                                                async success({latitude, longitude}) {
                                                    wx.setStorageSync('latitude', latitude)
                                                    wx.setStorageSync('longitude', longitude)
                                                    _this.getList({
                                                        latitude,
                                                        longitude,
                                                        pagination: _this.data.pagination,
                                                    })
                                                }
                                            })
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
    onPullDownRefresh() {

        const pagination = {
            ...this.data.pagination,
            page: 1,
        }
        this.getMerchants(pagination)
    },
    onReachBottom() {
        const pagination = {
            ...this.data.pagination,
            page: this.data.pagination.page + 1,
        }
        this.getMerchants(pagination)
    },
    onShareAppMessage() {
        return {
            title: '一键预约上门接收衣物,让生活更便捷',
            path: 'pages/home/index',
        }

    }
});
