import { hexMD5 } from "./MD5";
import { config } from '../config/index'

export const wxPay = ({ prepayId }, callback) => {
    const timeStamp = toString(new Date().getTime())
    const nonceStr = Math.random().toString(36).substr(2)
    console.log(prepayId);
    const { signType, key, appId } = config
    const paySign = hexMD5(`appId=${appId}&nonceStr=${nonceStr}&package=prepay_id=${prepayId}&signType=${signType}&timeStamp=${timeStamp}&key=${key}`)
    wx.requestPayment({
        timeStamp: timeStamp,
        nonceStr: nonceStr,
        package: `prepay_id=${prepayId}`,
        signType: signType,
        paySign: paySign,
        success() {
            callback({
                success: true,
                msg: '支付成功'
            })
        },
        fail() {
            callback({
                success: false,
                msg: '支付失败'
            })
        }
    })
}
