
'use strict';

var extend = require('util')._extend;
var Utils = require('./Utils');
var responsePreKey = 'imported';

/**
 *
 * @class DTOToResponseFuncConverter
 * @param {string} name
 * @param {object} dtoJson
 * @param {object} options
 * @param {Boolean} isEjs
 * @constructor
 *
 * DTO to Class converter
 */
function DTOToResponseFuncConverter(name, dtoJson, options, isEjs) {
	this.init(name, dtoJson, options, isEjs);
}

DTOToResponseFuncConverter.prototype = extend(DTOToResponseFuncConverter.prototype, Utils.prototype);
DTOToResponseFuncConverter.prototype = extend(DTOToResponseFuncConverter.prototype, {

	constructor: DTOToResponseFuncConverter,

	_defaults: {},

	/**
	 *
	 * @method init
	 * called by constructor
	 * @param {string} name
	 * @param {object} dtoJson
	 * @param {object} options
	 * @param {string} options.dirName
	 * @param {string} options.dest
	 * @param {string|undefined} options.responseFuncPath
	 * @param {boolean} isEjs
	 * @public
	 */
	init: function (name, dtoJson, options, isEjs) {

		options = extend(this._defaults, options || {});

		this._options = options;
		this._path = this._options.responseFuncPath;
		this._name = name;
		this._json = undefined;
		this._result = undefined;
		this._isEjs = isEjs;

		try {
			this._json = JSON.parse(JSON.stringify(dtoJson));
		} catch (err) {
			return;
		}
	},

	/**
	 * @method _isValid
	 * @returns {boolean}
	 * @private
	 */
	_isValid: function () {
		return (
			this.isFilledString(this._path) &&
			this.isFilledString(this._name) &&
			typeof this._options === 'object' &&
			typeof this._json === 'object'
		);
	},

	/**
	 * @method create
	 * @returns {object} result
	 * @public
	 */
	create: function () {

		if (!this._isValid()) {
			return {};
		}

		this._result = this._mapUnknown(this._json, '', []);
		return this._result;
	},

	/**
	 * @method _mapUnknown
	 * @param {Object} data
	 * @param {String} key
	 * @param {Array} path
	 * @private
	 */
	_mapUnknown: function (data, key, path) {

		if (data instanceof Array) {
			return this._mapArray(data, path);
		}

		if (typeof data === 'object') {
			return this._mapObject(data, path);
		}

		if (typeof data === 'string') {
			return this._mapTypeString(data, key);
		}

		return data;
	},

	/**
	 * @method _mapArray
	 * @param {Object} obj
	 * @param {Array} path
	 * @private
	 */
	_mapArray: function (obj, path) {

		var out = [];
		var tpl = this.readFile(__dirname + '/../src/templates/func-array.tpl');
		var methodStr;
		var data;

		if (!obj || !obj[0]) {
			return out;
		}

		data = JSON.stringify(this._mapUnknown(obj[0], '', path), null, 2);
		data = this._replaceWrapper(data);
		methodStr = tpl.replace('<%=data%>', data).replace(/(?:\r\n|\r|\n)/g, '').replace(/\t/g, '');

		if (this._isEjs) {
			return methodStr
				.replace(/"/g, '\'')
				// eslint-disable-next-line
				.replace(/'(<%-)(require\()([^\)]*)(\))(\.[^\)]*\))([^']*')/g, 'JSON.parse($2$3$4$5)')
				.replace(/;%>'/g, '')
				.replace(/'<%-/g, '')
				.replace(/%>'/g, '');
		}

		return '<%>' + methodStr + '<%>';
	},

	/**
	 * @method _mapObject
	 * @param {Object} obj
	 * @param {Array} path
	 * @private
	 */
	_mapObject: function (obj, path) {

		var objOut = {};

		this.forIn(obj, function (key, value) {
			var newPath = path.slice();
			newPath.push(key);
			objOut[key] = this._mapUnknown(value, key, newPath);
		});

		return objOut;
	},

	/**
	 * @method _mapTypeString
	 * @param {String} value
	 * @param {String|undefined} key
	 * @private
	 */
	_mapTypeString: function (value, key) {

		if (value.search(/^\$ref-/) >= 0) {
			return this._mapReference(value);
		}

		if (value === 'number') {
			return this._mapNumber();
		}

		if (value === 'string') {
			return this._mapString(key);
		}

		if (value === 'boolean') {
			return this._mapBoolean();
		}

		return value;
	},

	/**
	 * @method _mapString
	 * @param {String} key
	 * @private
	 */
	_mapString: function (key) {

		var _has;

		_has = function (search) {
			return this._isPart(key, search);
		}.bind(this);

		if (_has('name')) {

			if (_has('first')) {
				return this._getFaker('name.firstName', '');
			}
			if (_has('last')) {
				return this._getFaker('name.lastName', '');
			}
			if (_has('street')) {
				return this._getFaker('address.streetName', '');
			}
			return this._getFaker('name.findName', '');
		}

		if (_has('country')) {
			if (_has('code')) {
				return this._getFaker('random.arrayElement', '["CH","DE","AT","FR","UK","US","JP"]');
			}
			return this._getFaker('address.country', '');
		}

		if (_has('zip') || _has('postal')) {
			return this._getFaker('address.zipCode', '');
		}

		if (_has('city') || _has('town')) {
			return this._getFaker('address.city', '');
		}

		if (_has('street')) {
			if (_has('number')) {
				return this._getFaker('random.number', '');
			}
			return this._getFaker('address.streetName', '');
		}

		if (_has('phone')) {
			return this._getFaker('phone.phoneNumber', '');
		}

		if (_has('email')) {
			return this._getFaker('internet.email', '');
		}

		if (_has('userName')) {
			return this._getFaker('internet.userName', '');
		}

		if (_has('domain')) {
			return this._getFaker('internet.domainName', '');
		}

		if (_has('company')) {
			return this._getFaker('company.companyName', '');
		}

		if (_has('image')) {
			return this._getFaker('image.nature', '');
		}

		if (_has('title')) {
			if (_has('code')) {
				return this._getFaker('random.arrayElement', '["mr","ms"]');
			}
			return this._getFaker('name.prefix', '');
		}

		return this._getFaker('lorem.word', '');
	},

	/**
	 * @method _isPart
	 * @param {string} value
	 * @param {string} search
	 * @returns {boolean}
	 * @private
	 */
	_isPart: function (value, search) {
		return (value.toLowerCase().search(search) >= 0);
	},

	/**
	 * @method _mapNumber
	 * @returns {String}
	 * @private
	 */
	_mapNumber: function () {
		return this._getFaker('random.number', '');
	},

	/**
	 * @method _mapBoolean
	 * @returns {Boolean}
	 * @private
	 */
	_mapBoolean: function () {
		return this._getFaker('random.boolean', '');
	},

	/**
	 * @method _getFaker
	 * @param {String} path
	 * @param {String,undefined} args
	 * @returns {String}
	 * @private
	 */
	_getFaker: function (path, args) {

		if (this._isEjs) {
			return '<%-faker.' + path + '(' + args.replace(/"/g, '\'') + ')%>';
		}

		return '<%>faker.' + path + '(' + args + ')<%>';
	},

	/**
	 * @method _mapReference
	 * @param {String} refString
	 * @returns {String}
	 * @private
	 */
	_mapReference: function (refString) {
		var name = refString.replace('$ref-', '');

		if (this._isEjs) {
			return '<%-_getRef(\'' + name + '\');%>';
		}

		return '<%>_getRes(\'' + name + '\', loopArr)<%>';
	},

	/**
	 * @method _getFile
	 * @param {String} funcName
	 * @param {Object} data
	 * @returns {String}
	 * @private
	 */
	_getFile: function (funcName, data) {
		return this._replaceWrapper(this.readFile(__dirname + '/../src/templates/func.tpl')
			.replace(/<%=funcName%>/g, responsePreKey + funcName)
			.replace(/<%=data%>/g, JSON.stringify(data, null, 2))
		);
	},

	/**
	 * @method _replaceWrapper
	 * @param {String} value
	 * @returns {String}
	 * @private
	 */
	_replaceWrapper: function (value) {
		return value
			.replace(/"<%>/g, '')
			.replace(/<%>"/g, '')
			.replace(/\\"/g, '"')
			.replace(/\\"/g, '"')
			.replace(/""/g, '"')
		;
	},

	/**
	 * @method write
	 * @returns {void}
	 * @public
	 */
	write: function () {

		if (!this._isValid()) {
			return;
		}

		if (typeof this._result !== 'object') {
			this._result = {};
		}

		if (!this.existDir(this._path)) {
			this.writeDir(this._path);
		}

		this.writeFile(this._path + '/' + this._name + '.js', this._getFile(this._name, this._result));
	},

	/**
	 * @method get
	 * @returns {String}
	 * @public
	 */
	get: function () {

		if (!this._isValid()) {
			return '';
		}

		if (typeof this._result !== 'object') {
			this._result = {};
		}

		return this._result;
	},

});

module.exports = DTOToResponseFuncConverter;
