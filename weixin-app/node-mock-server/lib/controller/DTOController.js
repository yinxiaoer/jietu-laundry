
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();
var DTOToClassConverter = require('../DTOToClassConverter');

/**
 *
 * @class DTOController
 * @constructor
 *
 */
function DTOController() {
	this.init();
}

DTOController.prototype = extend(DTOController.prototype, Utils.prototype);
DTOController.prototype = extend(DTOController.prototype, {

	constructor: DTOController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.get('/service/class-dto', this._serviceDTOToClassConverter.bind(this));
	},

	/**
	 * @method _serviceDTOToClassConverter
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceDTOToClassConverter: function (req, res) {
		var json = JSON.parse(this.readFile(req.query.path));
		var dtoToClassConverter = new DTOToClassConverter(req.query.name, json, {
			jsVersion: req.query.es,
			isSetter: (req.query.setter === 'true'),
			isGetter: (req.query.getter === 'true'),
			isValidator: (req.query.validator === 'true'),
		}, this.options);
		res.send(dtoToClassConverter.get());
	},

});

module.exports = DTOController;
