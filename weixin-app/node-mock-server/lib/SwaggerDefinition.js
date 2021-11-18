
'use strict';

var SwaggerUtils = require('./SwaggerUtils');
var Utils = require('./Utils');
var extend = require('util')._extend;
var DTOToResponseFuncConverter = require('./DTOToResponseFuncConverter');

/**
 *
 * @class SwaggerDefinition
 * @namespace SwaggerImport
 * @param {string} name
 * @param {object} definition
 * @param {object} options
 * @constructor
 *
 * Swagger importer
 */
function SwaggerDefinition(name, definition, options) {
	this.init(name, definition, options);
}

SwaggerDefinition.prototype = extend(SwaggerDefinition.prototype, Utils.prototype);
SwaggerDefinition.prototype = extend(SwaggerDefinition.prototype, SwaggerUtils.prototype);
SwaggerDefinition.prototype = extend(SwaggerDefinition.prototype, {

	constructor: SwaggerDefinition,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @param {string} name
	 * @param {object} definition
	 * @param {object} options
	 * @returns void
	 * @public
	 */
	init: function (name, definition, options) {

		this._name = name;
		this._str = '';
		this._def = {};
		this._options = options;

		if (typeof definition === 'object') {
			this._def = this.restructureSchema(definition);
			this._str = JSON.stringify(this._def);
		}
	},

	/**
	 * @method mapReferences
	 * @returns {void}
	 * @public
	 */
	mapReferences: function () {
		this._def = JSON.parse(this._str);
	},

	/**
	 *
	 * @method setSwaggerDefinitions
	 * @param {object} swaggerDefinitions
	 * @public
	 */
	setSwaggerDefinitions: function (swaggerDefinitions) {
		this._swaggerDefinitions = swaggerDefinitions;
	},

	/**
	 *
	 * @method get
	 * @returns {object} definition
	 */
	get: function () {
		return this._def;
	},

	/**
	 *
	 * @method write
	 * @returns {void}
	 * @public
	 */
	write: function () {
		var dir = this._options.dest + '/_DTO';
		var file = dir + '/' + this._name + '.json';
		var dtoToResponseFuncConverter;

		dtoToResponseFuncConverter = new DTOToResponseFuncConverter(
			this._name,
			this._def,
			this._options,
			false
		);
		dtoToResponseFuncConverter.create();
		dtoToResponseFuncConverter.write();

		this.writeDir(dir);
		this.writeFile(file, JSON.stringify(this._def, null, 2));
	},

});

module.exports = SwaggerDefinition;
