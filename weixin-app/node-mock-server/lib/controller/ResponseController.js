
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var opener = require('opener');
var log = require('chip')();
var makeDir = require('make-dir');
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();
var GetResponse = require('../GetResponse');

/**
 *
 * @class ResponseController
 * @constructor
 *
 */
function ResponseController() {
	this.init();
}

ResponseController.prototype = extend(ResponseController.prototype, Utils.prototype);
ResponseController.prototype = extend(ResponseController.prototype, {

	constructor: ResponseController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.post('/service/expected-response', this._serviceWriteExpectedResponse.bind(this));
		// add get also to make it linkable
		appController.app.get('/service/expected-response', this._serviceWriteExpectedResponseLinkable.bind(this));
		appController.app.post('/service/response/:path/:method', this._serviceWriteNewResponse.bind(this));
		appController.app.delete('/service/response/:path/:method', this._serviceDeleteResponse.bind(this));
		appController.app.get('/view/response', this._viewResponse.bind(this));
		appController.app.post('/service/endpoint', this._serviceWriteNewEndpoint.bind(this));
	},

	/**
	 * @method _viewResponse
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_viewResponse: function (req, res) {
		var data;
		var response = new GetResponse({
			path: req.query.path,
			method: req.query.method,
			expected: req.query.expected,
		}, this.options);

		data = response.get();

		if (typeof data === 'object') {
			res.send(JSON.stringify(data, null, 2));
			res.end();
			return;
		}

		try {
			res.send(JSON.stringify(JSON.parse(data), null, 2));
			res.end();
			return;
		} catch (err) {
			res.send(data);
			res.end();
		}

		res.send(data);
		res.end();
	},

	/**
	 * @method _serviceDeleteResponse
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceDeleteResponse: function (req, res) {

		var path = decodeURIComponent(req.params.path);
		var method = decodeURIComponent(req.params.method).toUpperCase();
		var name = req.body.name;
		var filePath = path + '/' + method + '/mock/' + name;

		res.send({});

		if (this.existFile(filePath)) {
			this.removeFile(filePath);
			if (process.env.NODE_ENV !== 'test') {
				log.info('Deleted response file: ' + filePath);
			}
		}

		res.end();
	},

	/**
	 * @method _serviceWriteNewResponse
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceWriteNewResponse: function (req, res) {

		var path = decodeURIComponent(req.params.path);
		var method = decodeURIComponent(req.params.method).toUpperCase();
		var name = req.body.name;
		var filePath = path + '/' + method + '/mock/' + name;

		res.send({});

		if (!this.existFile(filePath)) {
			this.writeFile(filePath, '{}');
			if (process.env.NODE_ENV !== 'test') {
				log.info('Created new response file: ' + filePath);
			}
		}

		opener(filePath);

		res.end();
	},

	/**
	 * @method _serviceWriteNewEndpoint
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceWriteNewEndpoint: function (req, res) {

		var path = req.body.path.replace(/\/$/, '');
		var method = req.body.method;
		var desc = req.body.desc;
		var pathRoot = path.split('/')[1];
		var pathSub = path.split('/').splice(2);

		var filePath = this.options.restPath + '/' + pathRoot + '/#' + pathSub.join('#') + '/' + method;

		makeDir.sync(filePath);
		makeDir.sync(filePath + '/mock');

		var descData = {
			desc: desc,
			security: [],
			protected: false,
			status: '',
			response: {
				statusCode: 200,
				schema: {
					type: 'application/json',
				},
			},
		};
		this.writeFile(filePath + '/desc.json', JSON.stringify(descData, null, 2));
		this.writeFile(filePath + '/request_schema.json', '{}');
		this.writeFile(filePath + '/response_schema.json', '{}');
		this.writeFile(filePath + '/mock/success.json', '{}');
		this.writeFile(filePath + '/mock/error.json', '{}');
		this.writeFile(filePath + '/mock/empty.json', '{}');

		if (process.env.NODE_ENV !== 'test') {
			log.info('Wrote directory: ' + filePath);
			log.info('Wrote directory: ' + filePath + '/mock');
			log.info('Wrote file: ' + filePath + '/desc.json');
			log.info('Wrote file: ' + filePath + '/request_schema.json');
			log.info('Wrote file: ' + filePath + '/response_schema.json');
			log.info('Wrote file: ' + filePath + '/mock/success.json');
			log.info('Wrote file: ' + filePath + '/mock/error.json');
			log.info('Wrote file: ' + filePath + '/mock/empty.json');
		}

		res.redirect(this.options.uiPath);
	},

	/**
	 * @method _serviceWriteExpectedResponse
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceWriteExpectedResponse: function (req, res) {

		var path = req.body.path;
		var value = req.body.value;
		var linkableUrl = this.options.urlBase + '/service/expected-response?path=' +
			encodeURIComponent(path) +
			'&value=' + value;

		this.writeFile(path + 'response.txt', value);

		res.send({
			path: path,
			value: value,
			linkableUrl: linkableUrl,
		});

		if (process.env.NODE_ENV !== 'test') {
			log.info('Expected response set. Linkable Url: ' + linkableUrl);
		}

		res.end();
	},

	/**
	 * @method _serviceWriteExpectedResponseLinkable
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceWriteExpectedResponseLinkable: function (req, res) {
		this.writeFile(req.query.path + 'response.txt', req.query.value);
		res.send('Expected response set.<br/>value: ' + req.query.value + '<br/>path: ' + req.query.path);
		res.end();
	},

});

module.exports = ResponseController;
