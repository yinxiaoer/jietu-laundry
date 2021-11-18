
'use strict';

var nodePath = require('path');
var Utils = require('../Utils');
var extend = require('util')._extend;
var ejs = require('ejs');
var mime = require('mime-types');
var log = require('chip')();
var faker = require('faker');
var request = require('request');
var fs = require('fs');
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();

/**
 *
 * @class MockController
 * @constructor
 *
 */
function MockController() {
	this.init();
}

function _fetch(opt) {
	var isJson = opt.headers['content-type'] === 'application/json';

	request({
		uri: opt.url,
		method: opt.method || 'GET',
		body: isJson ? opt.data || {} : undefined,
		form: isJson ? undefined : opt.data || {},
		json: isJson,
		headers: opt.headers || {},
		gzip: true,
	},
	function (error, res, data) {
		if (error) {
			opt.error.call(this, error);
		} else {
			opt.success.call(this, data, res);
		}
	});
}

function _buildTunnelUrl(tunnelOpt) {
	var url = [];

	url.push(tunnelOpt.protocol);
	url.push('://');
	if (tunnelOpt.authUser && tunnelOpt.authPass) {
		url.push(tunnelOpt.authUser);
		url.push(':');
		url.push(tunnelOpt.authPass);
		url.push('@');
	}
	url.push(tunnelOpt.host);
	if (tunnelOpt.port && tunnelOpt.port !== 80) {
		url.push(':');
		url.push(tunnelOpt.port);
	}

	return url.join('');
}

MockController.prototype = extend(MockController.prototype, Utils.prototype);
MockController.prototype = extend(MockController.prototype, {

	constructor: MockController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.all('/*', this._handleMockRequest.bind(this));
	},

	/**
	 * @method _acceptMiddleware
	 * @param {Object} serverOptions
	 * @param {Object} responseOptions
	 * @private
	 */
	_acceptMiddleware: function (serverOptions, responseOptions) {
		var endPointId = responseOptions.dir.replace(serverOptions.dirName, '').replace(/\/$/, '');
		var middleware = serverOptions.middleware;

		if (!endPointId || !middleware || typeof middleware[endPointId] !== 'function') {
			responseOptions.res.statusCode = 500;
			responseOptions.res.end('Error: middleware for ' + endPointId + '" don\'t exist!');
			return false;
		}

		return middleware[endPointId](serverOptions, responseOptions);
	},

	/**
	 * @method _acceptTunnel
	 * @param {Object} serverOptions
	 * @param {Object} responseOptions
	 * @private
	 */
	_acceptTunnel: function (serverOptions, responseOptions) {
		var tunnelOpt = serverOptions.tunnel;
		var req = responseOptions.req;
		var res = responseOptions.res;
		var tunnelUrl = _buildTunnelUrl(tunnelOpt) + req.url;

		// append request headers from tunnel config
		if (tunnelOpt.requestHeaders) {
			req.headers = extend(req.headers, tunnelOpt.requestHeaders);
		}

		_fetch({
			url: tunnelUrl,
			method: req.method,
			data: req.body,
			headers: req.headers,
			success: function (data, response) {
				var statusCode = parseInt(response.statusCode, 10);
				var headersToSkip = ['transfer-encoding'];
				this.forIn(response.headers, function (key, value) {
					if (! headersToSkip.includes(key)) {
						res.setHeader(key, value);
					}
				});
				res.removeHeader('content-encoding');
				res.statusCode = response.statusCode;
				res.send(data);
				log.log('tunnel data from "' + tunnelUrl + '" to "' + req.url + '"');
				var tunnelFile = responseOptions.dir + 'mock/tunnel-latest.json';
				if (statusCode !== 200 && statusCode !== 201) {
					tunnelFile = responseOptions.dir + 'mock/tunnel-latest-' + statusCode + '.json';
				}
				this.writeFile(tunnelFile, data);
				log.log('save tunnel reponse in "' + tunnelFile + '""');
				res.end();
			}.bind(this),
			error: function (error) {
				log.error(error);
				res.statusCode = 500;
				res.send(error);
				res.end();
			},
		});
	},

	/**
	 * @method _getPath
	 * @param {string} originalUrl (/backend/v1/products/search)
	 * @param {string} urlPath (/backend/v1)
	 * @param {string} restPath (__dirname + /laundry/backend)
	 * @private
	 */
	_getPath: function (originalUrl, urlPath, restPath) {
		var urlPathRegExp = new RegExp(urlPath.replace(/{[^}]*}/g, '[^/]*'));
		return originalUrl.replace(urlPathRegExp, restPath);
	},

	/**
	 * @method _handleMockRequest
	 * @param {Object} req
	 * @param {Object} res
	 * @private
	 */
	_handleMockRequest: function (req, res) {

		var path = this._getPath(req.originalUrl, this.options.urlPath, this.options.restPath);
		var responseHeaders;
		var headers = Object.assign({}, this.options.headers);
		var options;

		if (path.search('favicon.ico') >= 0) {
			res.end();
			return true;
		}

		options = this.getResponseOptions(req, res);

		if (!options) {
			return;
		}

		if (options.expectedResponse.name === 'tunnel') {
			this._acceptTunnel(this.options, options);
			return;
		}

		// Fallback to success.json
		if (!this.existFile(options.responseFilePath)) {
			options.expectedResponse = {
				name: 'success',
				type: 'json',
			};
			options.responseFilePath = options.dir + 'mock/success.json';
			this.writeFile(options.dir + 'mock/response.txt', 'success');
		}

		// Add response headers
		if (this.existFile(options.responseHeadersFilePath)) {
			responseHeaders = JSON.parse(this.readFile(options.responseHeadersFilePath)) || {};
		}

		this._writeDefaultHeader(res, extend(headers, responseHeaders));

		setTimeout(function () {
			if (!this._hasValidDynamicPathParam(options)) {
				this._sendErrorEmptyPath(options);
			} else if (options.expectedResponse.name.search('error') >= 0) {
				this._sendError(options);
			} else if (options.method === 'HEAD') {
				this._sendHead(options);
			} else {
				this._sendSuccess(options);
			}
		}.bind(this), options.timeout);
	},

	/**
	 * @method _sendSuccess
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendSuccess: function (options) {

		if (options.expectedResponse.type === 'json') {
			this._sendSuccessJSON(options);
			return;
		}

		this._sendSuccessNotJSON(options);
	},

	/**
	 * @method _sendSuccessNotJSON
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendSuccessNotJSON: function (options) {
		try {
			if (this._setMimeType(options.res, options.expectedResponse.type)) {
				// add statusCode defined by filename
				var status = this._getHttpStatusFromFilePath(options.responseFilePath);
				if (status && options.res.statusCode === 200) {
					options.res.statusCode = status;
				}
				options.res.sendFile(options.responseFilePath);
				return;
			}
			log.log('unknown mime type');
			options.res.contentType('text/html');
			options.res.statusCode = 500;
			options.res.end('unknown mime type');
		} catch (err) {
			log.error(err);
			options.res.contentType('text/html');
			options.res.statusCode = 500;
			options.res.end(err);
		}
	},

	/**
	 * @method _sendSuccessJSON
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendSuccessJSON: function (options) {
		try {
			var responseFile = this.readFile(options.responseFilePath);
			var outStr;
			var ejsData = {
				body: this._getResponseData(options.req, options.method),
				require: require,
				faker: faker,
				params: this._getDynamicPathParams(options),
				query: options.req.query,
				headers: options.req.headers,
				__dirname: this.options.dirName,
			};

			ejsData = extend(ejsData, this._getFunc(this.options.funcPath));
			ejsData.response = this._getResponseFiles(options, ejsData);

			try {
				outStr = ejs.render(responseFile, ejsData);
			} catch (err) {
				log.error(err);
			}

			if (outStr) {
				// add statusCode defined by filename
				var status = this._getHttpStatusFromFilePath(options.responseFilePath);
				if (status && options.res.statusCode === 200) {
					options.res.statusCode = status;
				}
				options.res.send(outStr);
			} else {
				// this happens when something gone wrong with the ejs render
				options.res.statusCode = 500;
				options.res.send(responseFile);
			}
		} catch (err) {
			log.error(err);
			options.res.statusCode = 500;
			options.res.end();
		}
	},

	/**
	 * @method _setMimeType
	 * @param {Object} res
	 * @param {string} type
	 * @returns {boolean}
	 * @private
	 */
	_setMimeType: function (res, type) {
		var mimeType = mime.lookup(type);
		if (!mimeType) {
			return false;
		}
		res.contentType(mimeType);
		return true;
	},

	/**
	 * @method getResponseOptions
	 * @param {Object} req
	 * @param {Object} res
	 * @public
	 */
	getResponseOptions: function (req, res) {

		var path = this._getPath(req.originalUrl, this.options.urlPath, this.options.restPath);
		var method = req.method;
		var dir = this._processOptionsFallback(
			req,
			this._findFolder(path, this.options) + '/' + method + '/',
			this.options
		);
		var expectedResponse = this._getExpectedResponse(req, dir, this.readFile);
		var preferences = this.getPreferences(this.options);
		var timeout = 0;
		var responseFilePath = dir + 'mock/' + expectedResponse.name + '.' + expectedResponse.type;
		var responseHeadersFilePath = dir + 'mock/' + expectedResponse.name + '.headers.' + expectedResponse.type;

		if (preferences && preferences.responseDelay) {
			timeout = parseInt(preferences.responseDelay, 10);
		}

		if (expectedResponse.name === 'middleware') {
			var middlewareResponse = this._acceptMiddleware(this.options, {
				req: req,
				res: res,
				method: method,
				dir: dir,
				preferences: preferences,
			});

			if (typeof middlewareResponse === 'string') {
				expectedResponse = this._getExpectedResponse({}, '', function () { return middlewareResponse; });
				responseFilePath = dir + 'mock/' + expectedResponse.name + '.' + expectedResponse.type;
				responseHeadersFilePath = dir + 'mock/' + expectedResponse.name + '.headers.' + expectedResponse.type;
			} else {
				return;
			}
		}

		return {
			req: req,
			res: res,
			path: path,
			method: method,
			dir: dir,
			expectedResponse: expectedResponse,
			preferences: preferences,
			timeout: timeout,
			responseFilePath: responseFilePath,
			responseHeadersFilePath: responseHeadersFilePath,
		};
	},

	/**
	 * @method _processOptionsFallback
	 * @param {Object} req is the original request
	 * @param {String} directory for the response
	 * @param {Object} options
	 * @returns {String}
	 * @private
	 */
	_processOptionsFallback: function (req, dir, options) {
		if (!fs.existsSync(dir) && req.method === 'OPTIONS') {
			if (options.optionsFallbackPath) {
				if (options.optionsFallbackPath.slice(-1) === '/') {
					return options.optionsFallbackPath;
				}
				return options.optionsFallbackPath + '/';
			}
		}
		return dir;
	},

	/**
	 * @method _sendError
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendError: function (options) {
		var status = this._getHttpStatusFromFilePath(options.expectedResponse.name) || 500;

		// add statusCode defined by filename
		if (options.res.statusCode === 200) {
			options.res.statusCode = status;
		}

		this._setMimeType(options.res, options.expectedResponse.type);
		options.res.send(this.readFile(options.responseFilePath));
	},

	/**
	 * @method _sendError
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendErrorEmptyPath: function (options) {
		options.res.statusCode = 400;
		options.res.send(JSON.stringify({
			errors: [
				{
					message: 'Invalid path, please check the path params!',
					type: 'InvalidPathError',
				},
			],
		}));
	},

	/**
	 * @method _sendHead
	 * @param {Object} options
	 * @returns {void}
	 * @private
	 */
	_sendHead: function (options) {
		options.res.setHeader('X-Total-Count', Math.floor(Math.random() * 100));
		options.res.end();
	},

	/**
	 * @method _cleanPath
	 * @param {string} path
	 * @returns {string}
	 * @private
	 */
	_cleanPath: function (path) {
		return decodeURIComponent(path)
			.split('?')[0]
			.split('#')[0]
			.replace(/\/$/, '')
		;
	},

	/**
	 * @method _cleanDir
	 * @param {string} dir
	 * @param {string} method
	 * @returns {string}
	 * @private
	 */
	_cleanDir: function (dir, method) {

		var regDirReplace = new RegExp('\/' + method + '\/$');

		return dir.replace(regDirReplace, '')
			.replace(/#/g, '/')
			.replace(/\/\//g, '/')
			.replace(/\/$/, '')
		;
	},

	/**
	 * @method _hasValidDynamicPathParam
	 * @param {Object} options
	 * @returns {boolean}
	 * @private
	 */
	_hasValidDynamicPathParam: function (options) {

		var path = this._cleanPath(options.path);
		var pathSpl = path.split('/');
		var regMatchDyn = /^{([^}]*)}$/;
		var dir = this._cleanDir(options.dir, options.method);
		var dirSpl = dir.split('/');
		var result = true;

		// Ignore path length check in case of GET call because of fallback #68
		if (dirSpl.length !== pathSpl.length && options.method !== 'OPTIONS') {
			return false;
		}

		if (!this.existDir(options.dir)) {
			return false;
		}

		this.for(dirSpl, function (dirItem, i) {

			var exp = regMatchDyn.exec(dirItem);

			if (exp !== null && exp.length > 0) {
				if (pathSpl[i] === '' || regMatchDyn.test(pathSpl[i])) {
					result = false;
				}
			}
		});

		return result;

	},

	/**
	 * @method _getResponseFiles
	 * @param {Object} options
	 * @param {Object} ejsData
	 * @returns {Object}
	 * @private
	 */
	_getResponseFiles: function (options, ejsData) {

		var responses = {};
		var path = options.dir + 'mock';
		var files = this.readDir(path, ['.DS_Store']);

		this.for(files, function (filesObj) {
			try {
				var fileData = this.readFile(filesObj.path);
				if (filesObj.file.search(/.json$/) >= 0) {
					responses[filesObj.file.replace('.json', '')] = JSON.parse(ejs.render(fileData, ejsData));
				}
			} catch (err) {
				return;
			}
		}.bind(this));

		return responses;
	},

	/**
	 * @method _getExpectedResponse
	 * @param {Object} req
	 * @param {string} dir
	 * @param {Function} readFile
	 * @returns {string}
	 * @private
	 */
	_getExpectedResponse: function (req, dir, readFile) {

		var name;
		var fileName;
		var fileType;
		var type = 'json';
		var path = dir + 'mock/response.txt';

		try {
			fileName = readFile(path);
		} catch (err) {
			fileName = 'success';
		}

		fileType = fileName.match(/\.[a-zA-Z]*$/);
		if (fileType) {
			type = fileType[0].replace(/^\./, '').toLowerCase();
			name = fileName.replace(/\.[a-zA-Z]*$/, '');
		} else {
			name = fileName;
		}

		if (type === 'jpeg') {
			type = 'jpg';
		}

		if (req.query && typeof req.query._expected === 'string') {
			name = req.query._expected;
		}

		if (req.headers && typeof req.headers._expected === 'string') {
			name = req.headers._expected;
		}

		return {
			name: name,
			type: type,
		};
	},

	/**
	 * @method _getFunc
	 * @param {string|Array} path
	 * @returns {Object}
	 * @private
	 */
	_getFunc: function (path) {
		var _this = this;
		var func = {};
		var list = [];

		function addFunc(thisPath) {
			var out = {};

			try {
				// eslint-disable-next-line
				out = require(thisPath);
			} catch (err) {
				out = {};
			}

			func = extend(func, out);
		}

		function addDirectory(thisPath) {
			try {
				list = _this.readDir(thisPath, ['.DS_Store']);
			} catch (err) {
				if (process.env.NODE_ENV !== 'test') {
					log.error('Folder "' + thisPath + '" not found!');
				}
			}

			list.forEach(function (item) {
				addFunc(item.path);
			});
		}

		if (path instanceof Array) {
			path.forEach(function (itemPath) {
				addDirectory(itemPath);
			});
		} else if (typeof(path) === 'string' && path !== '') {
			addDirectory(path);
		}

		return func;
	},

	/**
	 * @method _getDynamicPathParams
	 * @param {Object} options
	 * @returns {Object}
	 * @private
	 */
	_getDynamicPathParams: function (options) {

		var path = options.path.split('?')[0].split('#')[0];
		var pathSpl = path.split('/');
		var regDirReplace = new RegExp('\/' + options.method + '\/$');
		var regMatchDyn = /^{([^}]*)}$/;
		var dir = options.dir.replace(regDirReplace, '').replace(/#/g, '/').replace(/\/\//g, '/');
		var dirSpl = dir.split('/');
		var params = {};

		if (dirSpl.length !== pathSpl.length) {
			return {};
		}

		this.for(dirSpl, function (dirItem, i) {

			var exp = regMatchDyn.exec(dirItem);

			if (exp !== null && exp.length > 0) {
				params[exp[1]] = pathSpl[i];
			}
		});

		return params;
	},

	/**
	 * @method _getResponseData
	 * @param {Object} req
	 * @param {string} method
	 * @returns {Object}
	 * @private
	 */
	_getResponseData: function (req, method) {
		switch (method) {
			case 'OPTIONS':	return req.body;
			case 'PUT':		return req.body;
			case 'PATCH':	return req.body;
			case 'DELETE':	return req.body;
			default:		return req.query;
		}
	},

	/**
	 *
	 * @method _isPathMatch
	 * @param {string} pathPatter
	 * @param {string} path
	 * @returns {boolean} isPathMatch
	 * @private
	 */
	_isPathMatch: function (pathPatter, path) {

		var pathPatterSpl = pathPatter.split('#');
		var pathSpl = path.split('#');

		pathSpl.forEach(function (pathItem, index) {
			var pathPatterItem = pathPatterSpl[index];

			// eslint-disable-next-line
			if (/({)(.*)(})/.test(pathPatterItem)) {
				pathSpl[index] = pathPatterItem;
			}
		});

		return pathSpl.join('#') === pathPatter;
	},

	/**
	 * @method _findFolder
	 * @param {string} path
	 * @param {Object} options
	 * @returns {Object}
	 * @private
	 */
	_findFolder: function (path, options) {

		path = path.split('?')[0];

		// removing trailing slashes to make it work on windows
		if (path.slice(-1) === '/') {
			path = path.slice(0, -1);
		}

		var pathArr = path.split('/');
		var restPathLength = options.restPath.split('/').length + 1;
		var pathRoot = pathArr.splice(0, restPathLength).join('/');
		var pathFolder = '#' + pathArr.join('#');
		var pathFolderArr;
		var dirs;
		var output = '';
		var i;

		if (pathFolder === '#') {
			return pathRoot + '/#';
		}

		dirs = this.readDir(pathRoot, ['.DS_Store']);
		pathFolderArr = pathFolder.split('#');

		for (i = 0; i < dirs.length; i += 1) {
			var item = dirs[i].file;
			var itemArr = item.split('#');

			if (item !== 'preferences.json' && itemArr.length === pathFolderArr.length) {

				if (item === pathFolder) {
					output = pathRoot + '/' + item;
					i = dirs.length + 1;
				} else if (this._isPathMatch(item, pathFolder)) {
					output = pathRoot + '/' + item;
					i = dirs.length + 1;
				}
			}
		}

		return output;
	},

	/**
	 * @method _writeDefaultHeader
	 * @param {Object} customHeaders
	 * @param {Object} res
	 * @returns {void}
	 * @private
	 */
	_writeDefaultHeader: function (res, customHeaders) {
		// set custom headers
		this.forIn(customHeaders, function (key, value) {
			res.setHeader(key, value);
		});
		res.setHeader('Content-Type', this.options.contentType);
		res.setHeader('Access-Control-Expose-Headers',
			typeof this.options.accessControlExposeHeaders === 'function' ?
				this.options.accessControlExposeHeaders(res.req) :
				this.options.accessControlExposeHeaders
		);
		res.setHeader('Access-Control-Allow-Origin',
			typeof this.options.accessControlAllowOrigin === 'function' ?
				this.options.accessControlAllowOrigin(res.req) :
				this.options.accessControlAllowOrigin
		);
		res.setHeader('Access-Control-Allow-Methods',
			typeof this.options.accessControlAllowMethods === 'function' ?
				this.options.accessControlAllowMethods(res.req) :
				this.options.accessControlAllowMethods
		);
		res.setHeader('Access-Control-Allow-Headers',
			typeof this.options.accessControlAllowHeaders === 'function' ?
				this.options.accessControlAllowHeaders(res.req) :
				this.options.accessControlAllowHeaders
		);
		res.setHeader('Access-Control-Allow-Credentials',
			typeof this.options.accessControlAllowCredentials === 'function' ?
				this.options.accessControlAllowCredentials(res.req) :
				this.options.accessControlAllowCredentials
		);
	},

	/**
	 * @method _getHttpStatusFromFilePath
	 * @param {string} filePath - absolute path to file
	 * @returns {number|void} HTTP status code
	 * @private
	 */
	_getHttpStatusFromFilePath: function (filePath) {
		var reg = (/([\w]+)(-)([0-9]{3})/).exec(nodePath.basename(filePath));

		if (reg === null) {
			return;
		}
		return parseInt(reg[3], 10);
	},

});

module.exports = MockController;
