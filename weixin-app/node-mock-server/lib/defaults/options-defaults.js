const { get } = require('lodash')
const config = require('../../config')
module.exports = {
	restPath: config.restPath,
	uiPath: config.uiPath,
	title: config.title,
	version: config.version,
	urlBase: get(config, 'baseUrl') || `localhost${config.port}`,
	urlPath: config.urlPath,
	port: get(config, 'port') || 3001,
	contentType: config.contentType,
	accessControlExposeHeaders: config.accessControlExposeHeaders,
	accessControlAllowOrigin: config.accessControlAllowOrigin,
	accessControlAllowMethods: config.accessControlAllowMethods,
	accessControlAllowHeaders: config.accessControlAllowHeaders,
	accessControlAllowCredentials: config.accessControlAllowCredentials,
	headers: config.headers,
	open: config.open,
};
