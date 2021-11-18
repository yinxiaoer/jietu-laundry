'use strict';

var log = require('chip')();
var Utils = require('../Utils');
var utils = new Utils();

function deleteCollection(id, options) {
	if (!utils.isFilledString(id)) {
		return false;
	}

	var path = `${options.restPath}/_collections/${id}.json`;

	if (!utils.existFile(path)) {
		log.error(`cannot delete collection ${id}: file "${path}" don\'t exist!`);
		return false;
	}

	utils.removeFile(path);
	return true;
}

module.exports = deleteCollection;
