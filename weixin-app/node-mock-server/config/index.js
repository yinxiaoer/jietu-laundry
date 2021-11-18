const { get } = require('lodash')
const program = process.env
const getConfig = () => {
	const isDevelopment = get(program, 'NODE_ENV') === 'development'
	if (isDevelopment) {
		return require('./dev.js')
	}
	return require('./local.js')
}

const config = getConfig()

module.exports = config
