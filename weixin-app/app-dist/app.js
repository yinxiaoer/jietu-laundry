import regeneratorRuntime from './utils/regenerator-runtime/runtime'
import http from './utils/http'
import {config} from './config/index'
import { mobile } from './utils/filter'
const lodash = {
    findIndex: require('./utils/lodash/findIndex'),
    find: require('./utils/lodash/find'),
    get: require('./utils/lodash/get'),
    map: require('./utils/lodash/map'),
    reduce: require('./utils/lodash/reduce'),
    isEmpty: require('./utils/lodash/isEmpty'),
    sortBy: require('./utils/lodash/sortBy'),
}
const dotProp = require('./utils/dopPropImmutable');
import { getFileType } from './utils/content'
exports.default = App({
    data: {
        toast: {
            show: true,
            msg: '32131',
            icon: 'no',
            iconColor: '#cccccc'
        }
    },
    fly: http.fly,
    lodash,
    dotProp,
    config: config,
    getFileType: getFileType,
    globalData: {
        userInfo: null,
    },
    async onLaunch() {
        this.setSystemInfo()
        const token = await wx.getStorageSync(`token${config.tokenKey}`)
        if(lodash.isEmpty(token)) {
            wx.reLaunch({
                url: '/pages/authorization/index'
            })
        }
    },
    setSystemInfo() {
        this.globalData.system = {
            height: wx.getSystemInfoSync().windowHeight,
            width: wx.getSystemInfoSync().windowWidth
        }
    },
    async getUserBaseInfo() {
        const res = await http.fly.post('/customer/getUserInfo')
        this.globalData.userInfo = {
            ...res.data,
            mobile: mobile(res.data.mobile)
        }
    },

    setUserInfo(res) {
        this.globalData.userInfo = {
            ...res.data,
            mobile: mobile(res.data.mobile)
        }
    },
    async getAddress(){
        const res = await http.fly.post('/customer/getAddresses')
        this.globalData.addresses = lodash.sortBy(res.data.addresses, address => !address.defaulted)
    },
    setAddress(addresses){
        this.globalData.addresses = lodash.sortBy(addresses, address => !address.defaulted)
    },
});
