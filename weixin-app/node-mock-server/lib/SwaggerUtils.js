
'use strict';

var Utils = require('../lib/Utils');
var extend = require('util')._extend;

/**
 *
 * @class SwaggerUtils
 * @namespace SwaggerImport
 * @constructor
 *
 * Swagger importer
 */
function SwaggerUtils() {
	this.init();
}

SwaggerUtils.prototype = extend(SwaggerUtils.prototype, Utils.prototype);
SwaggerUtils.prototype = extend(SwaggerUtils.prototype, {

	constructor: SwaggerUtils,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @returns void
	 * @public
	 */
	init: function () {

	},

	/**
	 * @method restructureSchema
	 * @param {object} swaggerSchema
	 * @returns {object}
	 * @public
	 */
	restructureSchema: function (swaggerSchema) {
		return this._map(swaggerSchema);
	},

	/**
	 * @method _map
	 * @param {object} object
	 * @returns {object}
	 * @privat
	 */
	_map: function (object) {
		if (typeof object.properties === 'object') {
			return this._mapObject(object.properties);
		}
		if (typeof object.$ref === 'string') {
			return this._mapRef(object.$ref);
		}
		return {};
	},

	/**
	 * @method _mapObject
	 * @param {object} object
	 * @returns {object}
	 * @privat
	 */
	_mapObject: function (object) {
		var out = {};

		this.forIn(object, function (key, value) {

			if (value.type && value.type === 'object') {
				out[key] = this._mapObject(value);
				return;
			}

			if (value.type && value.type === 'array' && value.items) {
				out[key] = [this._map(value.items)];
				return;
			}

			if (value.$ref && typeof value.$ref === 'string') {
				out[key] = this._mapRef(value.$ref);
				return;
			}

			if (value.type && typeof value.type === 'string') {
				out[key] = this._mapType(value.type);
			}
		});

		return out;
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

	/**
	 * @method _mapRef
	 * @param {string} value
	 * @returns {string}
	 * @privat
	 */
	_mapRef: function (value) {
		return '$ref-' + value.replace('#/definitions/', '');
	},

});

module.exports = SwaggerUtils;
