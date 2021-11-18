
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();
var Ui = require('../UserInterface');

/**
 *
 * @constructor UiController
 * @constructor
 *
 */
function UiController() {
	this.init();
}

UiController.prototype = extend(UiController.prototype, Utils.prototype);
UiController.prototype = extend(UiController.prototype, {

	constructor: UiController,

	/**
	 * @returns {void}
	 */
	init: function () {

		this.options = appController.options;

		appController.app.get(this.options.uiPath, this._renderUi.bind(this));
	},

	/**
	 * @param {Object} req - request data
	 * @param {Object} res - response data
	 * @returns {void}
	 * @private
	 */
	_renderUi: function (req, res) {
		var options = this.options;
		var ui = new Ui(this.options);
		var data = ui.get('data');

		res.render('default.ejs', {
			apiData: data,
			dataDto: ui.get('dataDto'),
			dataCollections: ui.get('dataCollections'),
			title: options.title,
			validation: ui.get('validation'),
			swaggerImport: ui.get('swaggerImport'),
			isSwaggerImportAvailable: ui.get('isSwaggerImportAvailable'),
			isCustomDTOToClassAvailable: ui.get('isCustomDTOToClassAvailable'),
			version: options.version,
			urlBase: options.urlBase,
			urlPath: options.urlPath,
		});
	},

});

module.exports = UiController;
