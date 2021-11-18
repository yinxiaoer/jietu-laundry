import request from '@/utils/request'

let upload = {
    uploadByBase64(data) {
        return request({
            url:'/uploadImgByBase64',
            method: 'post',
            data
        })
    }
}

export default upload



