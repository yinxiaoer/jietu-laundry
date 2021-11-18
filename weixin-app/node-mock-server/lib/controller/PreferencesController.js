
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();

/**
 *
 * @class PreferencesController
 * @constructor
 *
 */
function PreferencesController() {
	this.init();
}

PreferencesController.prototype = extend(PreferencesController.prototype, Utils.prototype);
PreferencesController.prototype = extend(PreferencesController.prototype, {

	constructor: PreferencesController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;
		this.preferencesFile = this.options.restPath + '/preferences.json';

		appController.app.post('/service/preferences', this._serviceWritePreferences.bind(this));
	},

	/**
	 * @method _serviceWritePreferences
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_serviceWritePreferences: function (req, res) {

		var data = this.getPreferences(this.options);

		data[req.body.key] = req.body.value;

		this.writeFile(this.preferencesFile, JSON.stringify(data));

		res.end();
	},

});

module.exports = PreferencesController;
