
'use strict';

var Utils = require('../Utils');
var util = require('util');
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();

/**
 *
 * @class ViewSchemaController
 * @constructor
 *
 */
function ViewSchemaController() {
	this.init();
}

ViewSchemaController.prototype = extend(ViewSchemaController.prototype, Utils.prototype);
ViewSchemaController.prototype = extend(ViewSchemaController.prototype, {

	constructor: ViewSchemaController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.get('/view/schema', this._viewFormatted.bind(this));
		appController.app.get('/view/schema/file', this._viewFile.bind(this));
	},

	/**
	 * @method _viewFile
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_viewFile: function (req, res) {
		if (!req.query || !req.query.url) {
			res.send('Not Found');
			return;
		}

		try {
			res.send(this.readFile(req.query.url));
		} catch (err) {
			res.send('Not Found');
		}
	},

	/**
	 * @method _viewFormatted
	 * @param {object} req
	 * @param {object} res
	 * @private
	 */
	_viewFormatted: function (req, res) {
		if (!req.query || !req.query.url) {
			res.send('Not Found');
			return;
		}

		try {
			var method = req.query.method || 'GET';
			var title = (req.query.type || '') + ' Schema';
			var path = (req.query.path || '');

			res.render('service-schema.ejs', {
				pageTitle: title,
				title: title + ' | ' + method + ' | ' + path,
				subTitle: path,
				method: method,
				methodLower: method.toLowerCase(),
				schemaJSON: this.readFile(req.query.url),
			});

		} catch (err) {
			res.send('Not Found');
		}
	},

});

module.exports = ViewSchemaController;
