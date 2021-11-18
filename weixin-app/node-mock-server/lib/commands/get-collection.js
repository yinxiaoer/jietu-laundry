'use strict';

var log = require('chip')();
var Utils = require('../Utils');
var utils = new Utils();

function getCollection(id, options) {

	if (!utils.isFilledString(id)) {
		return false;
	}

	var path = `${options.restPath}/_collections/${id}.json`;

	if (!utils.existFile(path)) {
		log.error(`cannot read collection ${id}: file "${path}" don\'t exist!`);
		return false;
	}

	return JSON.parse(utils.readFile(path));
}

module.exports = getCollection;
