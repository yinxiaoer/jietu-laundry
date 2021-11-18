
'use strict';

var extend = require('util')._extend;
var validatorLog = require('./ValidatorLog');
var ValidatorResponse = require('./ValidatorResponse');
var Utils = require('./Utils');
var ignoreInRestRoot = require('./constants/ignore-in-rest-root');
var ignoreGeneral = require('./constants/ignore-general');

/**
 * @constructor ValidatorResponses
 * @param {Object} options - the options
 * @param {Object} serverOptions - the node-laundry options
 */
function ValidatorResponses(options, serverOptions) {
	this.init(options, serverOptions);
}

ValidatorResponses.prototype = extend(ValidatorResponses.prototype, Utils.prototype);
ValidatorResponses.prototype = extend(ValidatorResponses.prototype, {

	constructor: ValidatorResponses,

	_defaults: {
		restPath: '',
	},

	/**
	 * called by constructor
	 * @param {Object} options - the options
	 * @param {Object} serverOptions - the node-laundry options
	 * @returns {void}
	 * @public
	 */
	init: function (options, serverOptions) {

		options = extend(this._defaults, options || {});

		validatorLog.clear();
		validatorLog.neutral('response validation\'s started');

		this._options = options;
		this._serverOptions = serverOptions;
		this._prefFilePath = this._options.restPath + '/preferences.json';
		this._validatorFilePath = this._options.restPath + '/.validation.json';
		this._tempPrefData = '';
		this._setResponseDelay();
		this._validationResponses = this._getValidationResponses(this._options.restPath);
		this._runValidations(this._validationResponses);
		this._restoreResponseDelay();

	},

	/**
	 * @returns {void}
	 * @private
	 */
	_restoreResponseDelay: function () {
		this.writeFile(this._prefFilePath, this._tempPrefData);
	},

	/**
	 * @returns {void}
	 * @private
	 */
	_setResponseDelay: function () {

		var prefData;
		var prefDataJson;

		if (!this.existFile(this._prefFilePath)) {
			this._tempPrefData = '{}';
			return;
		}

		prefData = this.readFile(this._prefFilePath);
		prefDataJson = JSON.parse(prefData);

		this._tempPrefData = prefData;

		prefDataJson.responseDelay = '0';

		this.writeFile(this._prefFilePath, JSON.stringify(prefDataJson));
	},

	/**
	 * @param {Array} validationResponses - list of responses
	 * @returns {void}
	 * @private
	 */
	_runValidations: function (validationResponses) {
		this.for(validationResponses, function (item) {
			this._runValidation(item);
		}.bind(this));
		var date = new Date();
		this.writeFile(this._validatorFilePath, JSON.stringify({
			messages: validatorLog.get(),
			dateTime: date.toISOString().replace(/T/, ' ').replace(/\..+/, '') + ' GMT+0000',
		}));
	},

	/**
	 * @param {Object} validation - one validation entry
	 * @param {string} validation.path - validation path
	 * @param {string} validation.method - validation method eg. GET, GET, ...
	 * @param {string} validation.expected - validation expected
	 * @returns {ValidatorResponse} returns the validation response
	 * @private
	 */
	_runValidation: function (validation) {
		return new ValidatorResponse({
			path: validation.path,
			method: validation.method,
			expected: validation.expected,
		}, this._serverOptions);
	},

	/**
	 * @param {string} path - validation path
	 * @returns {Array} validationResponses
	 * @private
	 */
	_getValidationResponses: function (path) {
		var out = [];
		var rootDirArray = this.readDir(path, ignoreInRestRoot);

		this.for(rootDirArray, function (rootItem) {

			var pathDirArray = this.readDir(rootItem.path, ignoreGeneral);
			this.for(pathDirArray, function (pathItem) {

				var pathItemPath = pathItem.path;

				var methodDirArray = this.readDir(pathItemPath, ignoreGeneral);
				this.for(methodDirArray, function (methodItem) {

					var method = methodItem.file;

					var mockDirArray = this.readDir(methodItem.path + '/mock', ignoreGeneral.concat(['response.txt']));
					this.for(mockDirArray, function (mockItem) {

						var isErrorFile = (mockItem.file.search(/error/g) >= 0);
						var isStoreFile = (mockItem.file.search(/^\./g) >= 0);
						var isHeaderFile = (mockItem.file.search(/\.headers\.json$/) >= 0);

						if (!isErrorFile && !isStoreFile && !isHeaderFile) {
							out.push({
								path: pathItemPath,
								method: method,
								expected: mockItem.file.replace('.json', ''),
							});
						}
					});
				}.bind(this));
			}.bind(this));
		}.bind(this));

		return out;
	},

});

module.exports = ValidatorResponses;
