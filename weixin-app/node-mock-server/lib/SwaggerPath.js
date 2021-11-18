
'use strict';

var SwaggerPathMethod = require('./SwaggerPathMethod');
var Utils = require('./Utils');
var extend = require('util')._extend;

/**
 *
 * @class SwaggerPath
 * @namespace SwaggerImport
 * @param {string} name
 * @param {object} pathObject
 * @param {object} options
 * @constructor
 *
 * Swagger importer
 */
function SwaggerPath(name, pathObject, options) {
	this.init(name, pathObject, options);
}

SwaggerPath.prototype = extend(SwaggerPath.prototype, Utils.prototype);
SwaggerPath.prototype = extend(SwaggerPath.prototype, {

	constructor: SwaggerPath,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @param {string} name
	 * @param {object} pathObject
	 * @param {object} options
	 * @returns void
	 * @public
	 */
	init: function (name, pathObject, options) {

		this._options = options;
		this._name = name;
		this._cleanedName = name.replace(this._options.replacePathsStr, '');
		this._groupName = this._cleanedName.split('/')[1];
		this._pathName = this._cleanedName.split('/');
		this._pathName = '#' + this._pathName.slice(2, this._pathName.length).join('#');
		this._swaggerPathMethods = {};
		this._pathObject = pathObject;
		this._swaggerDefinitions = {};

	},

	/**
	 *
	 * @method setDefinitions
	 * @param {object} swaggerDefinitions
	 * @returns {void}
	 * @protected
	 */
	setDefinitions: function (swaggerDefinitions) {
		this._swaggerDefinitions = swaggerDefinitions;
	},

	/**
	 *
	 * @method getDefinitions
	 * @returns {object} swaggerDefinitions
	 * @protected
	 */
	getDefinitions: function () {
		return this._swaggerDefinitions || {};
	},

	/**
	 *
	 * @method write
	 * @returns void
	 * @public
	 */
	write: function () {

		var dirGroup = this._options.dest + '/' + this._groupName;
		var dirPath = dirGroup + '/' + this._pathName;

		this._storeMethods();
		this.writeDir(this._options.dest);
		this.writeDir(dirGroup);
		this.writeDir(dirPath);
		this.forIn(this._swaggerPathMethods, function (name, swaggerPathMethod) {
			swaggerPathMethod.write(dirPath);
		});
	},

	/**
	 *
	 * @method _storeMethods
	 * @returns void
	 * @private
	 */
	_storeMethods: function () {
		this.forIn(this._pathObject, function (key, value) {
			var name = key.toUpperCase();
			var swaggerPathMethod;

			swaggerPathMethod = new SwaggerPathMethod(name, value, this._options);
			swaggerPathMethod.setDefinitions(this.getDefinitions());

			this._swaggerPathMethods[name] = swaggerPathMethod;
		});
	},

});

module.exports = SwaggerPath;
