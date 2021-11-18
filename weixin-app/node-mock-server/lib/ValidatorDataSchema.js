
'use strict';

var extend = require('util')._extend;
var validatorLog = require('./ValidatorLog');
var Utils = require('./Utils');

/**
 *
 * @class ValidatorDataSchema
 * @namespace ValidatorDataSchema
 * @param {object} options
 * @constructor
 *
 * Swagger importer
 */
function ValidatorDataSchema(options) {
	this.init(options);
}

ValidatorDataSchema.prototype = extend(ValidatorDataSchema.prototype, Utils.prototype);
ValidatorDataSchema.prototype = extend(ValidatorDataSchema.prototype, {

	constructor: ValidatorDataSchema,

	_defaults: {
		schema: undefined,
		data: undefined,
	},

	/**
	 *
	 * @method init
	 * called by constructor
	 * @param {object} options
	 * @public
	 */
	init: function (options) {

		var _validation = {};
		var cleanedPath = this.cleanPath(options, options.filePathData);

		options = extend(this._defaults, options || {});

		this._options = options;
		this._isValid = true;
		this._schema = this._options.schema;
		this._data = this._options.data;
		this._path = this._options.path;
		this._expected = this._options.expected;
		this._schemaMap = {};
		this._invalidCounter = 0;

		if (!this._schema || typeof this._schema !== 'object') {
			this._isValid = false;
			validatorLog.error('<code>options.schema</code> can\'t be empty!');
		}

		if (!this._data || typeof this._data !== 'object') {
			this._isValid = false;
		}

		this._mapSchema(this._schema);
		this._mapData(this._data);

		_validation.validation = {};
		_validation.validation[this._expected] = {};
		_validation.validation[this._expected].counter = this._invalidCounter;

		this.setMethodStore(this._path, _validation);

		if (this._invalidCounter > 0) {
			validatorLog.error('Mock data in file <code>' + cleanedPath + '</code> are invalid! ' +
				'<span class="label label-danger">' + this._invalidCounter + '</span> invalid values found.');

			return false;
		}

		validatorLog.success('Mock data in file <code>' + cleanedPath + '</code> are valid!');
		return true;
	},

	/**
	 * @method _mapSchema
	 * @param {object|Array} schema
	 * @returns {void}
	 * @private
	 */
	_mapSchema: function (schema) {
		this._mapSchemaUnknown({
			data: schema,
			path: [],
			refs: [],
		});
	},

	/**
	 * @method _mapSchemaUnknown
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {object} opt.data
	 * @param {Array} opt.refs
	 * @returns {Boolean} isMapped
	 * @private
	 */
	_mapSchemaUnknown: function (opt) {

		if (opt.data instanceof Array) {
			if (opt.data.length > 0) {
				this._mapSchemaArray({
					data: opt.data[0],
					path: opt.path,
					refs: opt.refs,
				});
			}
			return true;
		}

		if (typeof opt.data === 'object') {
			this._mapSchemaObject({
				data: opt.data,
				path: opt.path,
				refs: opt.refs,
			});
			return true;
		}

		return false;
	},

	/**
	 * @method _mapSchemaArray
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {*} opt.data
	 * @param {Array} opt.refs
	 * @returns {void}
	 * @private
	 */
	_mapSchemaArray: function (opt) {

		this._setTmpSchemaObj({
			path: opt.path.slice(),
			type: 'array',
			refs: opt.refs,
		});

		var path = opt.path.slice();
		var newPath;

		path.push('[]');

		if (typeof opt.data !== 'object' || opt.data instanceof Array) {
			newPath = path.slice();

			if (opt.data instanceof Array) {
				if (opt.data.length > 0) {
					this._mapSchemaArray({
						data: opt.data[0],
						path: newPath,
						refs: opt.refs,
					});
				}
				return;
			}

			this._mapSchemaString({
				data: opt.data,
				path: newPath,
				refs: opt.refs,
			});
			return;
		}

		this.forIn(opt.data, function (key, value) {

			newPath = path.slice();

			if (
				this._mapSchemaUnknown({
					data: value,
					path: newPath,
					refs: opt.refs,
				})
			) {
				return;
			}

			newPath.push(key);

			this._mapSchemaString({
				data: value,
				path: newPath,
				refs: opt.refs,
			});

		}.bind(this));
	},

	/**
	 * @method _mapSchemaObject
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {object} opt.data
	 * @param {Array} opt.refs
	 * @returns {void}
	 * @private
	 */
	_mapSchemaObject: function (opt) {

		this._setTmpSchemaObj({
			path: opt.path.slice(),
			type: 'object',
			refs: opt.refs,
		});

		this.forIn(opt.data, function (key, value) {
			var newPath = opt.path.slice();

			newPath.push(key);

			if (
				this._mapSchemaUnknown({
					data: value,
					path: newPath,
					refs: opt.refs,
				})
			) {
				return;
			}

			this._mapSchemaString({
				data: value,
				path: newPath,
				refs: opt.refs,
			});

			return true;
		}.bind(this));
	},

	/**
	 * @method _mapSchemaString
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {String} opt.data
	 * @param {Array} opt.refs
	 * @returns {void}
	 * @private
	 */
	_mapSchemaString: function (opt) {

		var regResult = (/(^\$ref-)(.*)/).exec(opt.data);

		if (regResult !== null && regResult.length > 2) {

			var ref = this._getRef(regResult[2]);
			var refs = opt.refs.slice();
			var found1 = refs.indexOf(regResult[2]);

			refs.push(regResult[2]);

			if (found1 >= 0) {
				if (refs.indexOf(regResult[2], found1) >= 0) {
					return;
				}
			}

			if (ref instanceof Array) {
				if (ref.length > 0) {
					this._mapSchemaArray({
						data: ref[0],
						path: opt.path,
						refs: refs,
					});
				}
				return;
			}

			if (typeof ref === 'object') {
				this._mapSchemaObject({
					data: ref,
					path: opt.path,
					refs: refs,
				});
				return;
			}

			return;
		}

		this._setTmpSchemaObj({
			path: opt.path,
			type: this._mapType(opt.data),
		});
	},

	/**
	 * @method _getRef
	 * @param {String} refName
	 * @returns {Object|Array}
	 * @private
	 */
	_getRef: function (refName) {
		try {
			return JSON.parse(this.readFile(this._options.restPath + '/_DTO/' + refName + '.json'));
		} catch (err) {
			return {};
		}
	},

	/**
	 * @method _mapData
	 * @param {object} data
	 * @returns {void}
	 * @private
	 */
	_mapData: function (data) {

		if (data instanceof Array) {
			this._mapDataArray({
				data: data,
				path: [],
			});
		} else if (typeof data === 'object') {
			this._mapDataObject({
				data: data,
				path: [],
			});
		}
	},

	/**
	 * @method _mapDataObject
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {Object|Array} opt.data
	 * @returns {void}
	 * @private
	 */
	_mapDataObject: function (opt) {
		this.forIn(opt.data, function (key, value) {
			var newPath = opt.path.slice();
			var type = this._getType(value);
			var isObject = (type === 'object');
			var isArray = (type === 'array');

			newPath.push(key);

			if (isObject || isArray) {
				this._validateObject({
					path: newPath,
					type: type,
				});
			} else {
				this._validateObject({
					path: newPath,
					type: type,
					value: value,
				});
			}

			if (isObject) {
				this._mapDataObject({
					data: value,
					path: newPath,
				});
				return;
			}

			if (isArray) {
				this._mapDataArray({
					data: value,
					path: newPath,
				});
			}

		}.bind(this));
	},

	/**
	 * @method _validateObject
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {String} opt.type
	 * @param {*} opt.value
	 * @returns {void}
	 * @private
	 */
	_validateObject: function (opt) {

		var keyName = this._getSchemaKeyName(opt);
		var item = this._schemaMap[keyName];
		var pathStr = opt.path.join('.');

		if (!item) {
			validatorLog.error('No schema definition for: <code>' + pathStr + '</code> found, ' +
				'please check you schema and/or mock file!');
			this._invalidCounter += 1;
			return;
		}

		if (item !== opt.type) {
			validatorLog.error('invalid type for: <code>' + pathStr + '</code> ' +
				'with value <code>' + opt.value + '</code> found! ' +
				'Value needs to by type of <code>' + item + '</code>.'
			);
			this._invalidCounter += 1;
		}
	},

	/**
	 * @method _setTmpSchemaObj
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {String} opt.type
	 * @returns {void}
	 * @private
	 */
	_setTmpSchemaObj: function (opt) {

		if (!(opt.path instanceof Array) || opt.path.length < 1) {
			return;
		}

		this._schemaMap[this._getSchemaKeyName(opt)] = opt.type;
	},

	/**
	 * @method _getSchemaKeyName
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @returns {String}
	 * @private
	 */
	_getSchemaKeyName: function (opt) {
		var fileName = opt.path.join('.');

		fileName = fileName.trim();
		return this._getHashCode(fileName);
	},

	/**
	 * @method _getHashCode
	 * @param {String} s
	 * @returns {String}
	 * @private
	 */
	_getHashCode: function (s) {
		// eslint-disable-next-line
		return s.split('').reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);
	},

	/**
	 * @method _mapDataArray
	 * @param {object} opt
	 * @param {Array} opt.path
	 * @param {Object|Array} opt.data
	 * @returns {void}
	 * @private
	 */
	_mapDataArray: function (opt) {

		var path = opt.path.slice();
		path.push('[]');

		this.for(opt.data, function (value) {

			var newPath = path.slice();
			var type = this._getType(value);

			if (type === 'object') {
				this._mapDataObject({
					data: value,
					path: newPath,
				});
				return;
			}

			if (type === 'array') {
				this._mapDataArray({
					data: value,
					path: newPath,
				});
			}

		}.bind(this));

	},

	/**
	 * @method _getType
	 * @param {*} value
	 * @returns {string} type
	 * @private
	 */
	_getType: function (value) {

		if (value instanceof Array) {
			return 'array';
		}

		return (typeof value).toLowerCase();
	},

	/**
	 * @method _mapType
	 * @param {*} type
	 * @returns {string} type
	 * @private
	 */
	_mapType: function (type) {

		switch (type) {
			case 'integer': return 'number';
			case 'float': return 'number';
			case 'bigdezimal': return 'number';
			default: return type;
		}

	},

});

module.exports = ValidatorDataSchema;
