
'use strict';

var Utils = require('../Utils');
var util = require('util');
var log = require('chip')();
var extend = util._extend;
var AppControllerSingleton = require('./AppController');
var appController = AppControllerSingleton.getInstance();
var deleteCollection = require('../commands/delete-collection');
var activateCollection = require('../commands/activate-collection');
var getCurrentlySelectedResponses = require('../commands/get-currently-selected-responses');

/**
 *
 * @class CollectionController
 * @constructor
 *
 */
function CollectionController() {
	this.init();
}

CollectionController.prototype = extend(CollectionController.prototype, Utils.prototype);
CollectionController.prototype = extend(CollectionController.prototype, {

	constructor: CollectionController,

	/**
	 *
	 * @method init
	 * called by constructor
	 * @public
	 */
	init: function () {

		this.options = appController.options;

		appController.app.delete('/service/collection/:id', this.handleDeleteCollection.bind(this));
		appController.app.post('/service/collection/:id/activate', this.handleActivateCollection.bind(this));
		appController.app.post('/service/collection', this.handleSubmitNewCollection.bind(this));
	},

	/**
	 * @method handleSubmitNewCollection
	 * @param {object} req
	 * @param {object} res
	 * @returns {void}
	 */
	handleSubmitNewCollection: function (req, res) {

		var options = this.options;
		var name = req.body.name;
		var id = this.cleanString(name);
		var description = req.body.description || '';

		if (!this.isFilledString(name) || !this.isFilledString(id)) {
			res.statusCode = 500;
			res.end('Error, please try again later!');
			return;
		}

		var selectedResponses = getCurrentlySelectedResponses(options);
		var collection = {
			name,
			description,
			selectedResponses,
		};
		var collectionFilePath = `${options.restPath}/_collections/${id}.json`;

		this.writeFile(collectionFilePath, JSON.stringify(collection, null, 2));
		log(`Wrote file "${collectionFilePath}"`);

		res.redirect('/');
	},

	/**
	 * @method handleActivateCollection
	 * @param {object} req
	 * @param {object} res
	 */
	handleActivateCollection: function (req, res) {
		var wasActivated = activateCollection(req.params.id, this.options);

		if (!wasActivated) {
			res.statusCode = 500;
			res.end();
			return;
		}
		res.end();
	},

	/**
	 * @method handleDeleteCollection
	 * @param {object} req
	 * @param {object} res
	 */
	handleDeleteCollection: function (req, res) {
		var wasDeleted = deleteCollection(req.params.id, this.options);

		if (!wasDeleted) {
			res.statusCode = 500;
			res.end();
			return;
		}
		res.end();
	},

});

module.exports = CollectionController;
