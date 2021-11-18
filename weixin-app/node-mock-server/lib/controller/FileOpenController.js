
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var opener = require('opener');
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();

/**
 *
 * @class FileOpenController
 * @constructor
 *
 */
function FileOpenController() {
	this.init();
}

FileOpenController.prototype = extend(FileOpenController.prototype, Utils.prototype);
FileOpenController.prototype = extend(FileOpenController.prototype, {

	constructor: FileOpenController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.get('/service/open', this.handleOpen.bind(this));
	},

	/**
	 * @method handleOpen
	 * @param {object} req
	 * @param {object} res
	 * @returns {void}
	 */
	handleOpen: function (req, res) {
		opener(req.query.path);
		res.end();
	},

});

module.exports = FileOpenController;
