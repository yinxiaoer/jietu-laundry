const { fly } = require('../http/request')

module.exports = {
    checkUser: fly.post('/checkUser'),
}
