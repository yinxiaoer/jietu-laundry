
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();
var SwaggerImport = require('../SwaggerImport');
var swaggerLog = require('../SwaggerLog');

/**
 *
 * @class SwaggerImportController
 * @constructor
 *
 */
function SwaggerImportController() {
	this.init();
}

SwaggerImportController.prototype = extend(SwaggerImportController.prototype, Utils.prototype);
SwaggerImportController.prototype = extend(SwaggerImportController.prototype, {

	constructor: SwaggerImportController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.get('/service/swagger-import', this._serviceSwaggerImport.bind(this));
	},

	/**
	 * @method _serviceSwaggerImport
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceSwaggerImport: function (req, res) {
		var swaggerImporter = new SwaggerImport(this.options.swaggerImport);
		swaggerImporter.doImport(function () {
			res.send(swaggerLog.get());
			res.end();
		});
	},

});

module.exports = SwaggerImportController;
