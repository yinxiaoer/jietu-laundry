
'use strict';

var extend = require('util')._extend;
var GetResponse = require('./GetResponse');
var validatorLog = require('./ValidatorLog');
var ValidatorDataSchema = require('./ValidatorDataSchema');
var Utils = require('./Utils');

/**
 *
 * @class ValidatorResponse
 * @namespace ValidatorResponse
 * @param {object} options
 * @param {object} serverOptions
 * @constructor
 *
 * Swagger importer
 */
function ValidatorResponse(options, serverOptions) {
	this.init(options, serverOptions);
}

ValidatorResponse.prototype = extend(ValidatorResponse.prototype, Utils.prototype);
ValidatorResponse.prototype = extend(ValidatorResponse.prototype, {

	constructor: ValidatorResponse,

	_defaults: {
		path: undefined,
		method: undefined,
		expected: undefined,
	},

	/**
	 *
	 * @method init
	 * called by constructor
	 * @param {object} options
	 * @param {object} serverOptions
	 * @public
	 */
	init: function (options, serverOptions) {

		options = extend(this._defaults, options || {});

		this._options = options;
		this._serverOptions = serverOptions;
		this._isValid = true;
		this._path = this._options.path;
		this._method = this._options.method.toUpperCase();
		this._expected = this._options.expected;

		if (!this.isFilledString(this._path)) {
			this._isValid = false;
			validatorLog.error('<code>options.path</code> can\'t be empty!');
		}

		if (!this.isFilledString(this._method)) {
			this._isValid = false;
			validatorLog.error('<code>options.method</code> can\'t be empty!');
		}

		if (!this.isFilledString(this._expected)) {
			this._isValid = false;
			validatorLog.error('<code>options.expected</code> can\'t be empty!');
		}

		if (this._isValid) {
			this._pathMethod = this._path + '/' + this._method;
			this._pathSchema = this._pathMethod + '/response_schema.json';
			this._pathMockData = this._pathMethod + '/mock/' + this._expected;
			this._dataSchema = {};
			this._dataExpected = {};
			this._readFiles();
		}

		if (this._isValid) {
			return new ValidatorDataSchema({
				schema: this._dataSchema,
				data: this._dataExpected,
				path: this._pathMethod,
				expected: this._expected,
				restPath: this._serverOptions.restPath,
				filePathData: this._pathMockData,
			});
		}

	},

	/**
	 * @method _readFiles
	 * @returns {void}
	 * @private
	 */
	_readFiles: function () {

		var response;
		var cleanedPath = this._cleanPath(this._serverOptions, this._pathMockData);

		if (this._isValid) {
			try {
				this._dataSchema = JSON.parse(this.readFile(this._pathSchema));
			} catch (err) {
				validatorLog.success('Mock data in file <code>' + cleanedPath + '</code> are valid!');
				this._isValid = false;
			}

			response = new GetResponse({
				path: this._path,
				method: this._method,
				expected: this._expected,
			}, this._serverOptions);

			this._dataExpected = response.get();
		}
	},

	/**
	 *
	 * @method cleanPath
	 * @param {Object} options
	 * @param {String} path
	 * @returns {String}
	 * @private
	 */
	_cleanPath: function (options, path) {

		if (typeof path !== 'string') {
			return '';
		}

		if (typeof options !== 'object' || !options.restPath) {
			return path;
		}

		return path.replace(options.restPath, '');
	},

});

module.exports = ValidatorResponse;
