
var log = require('chip')();
var Utils = require('../Utils');
var utils = new Utils();

function checkAndCreateDirectory(path) {
	if (!utils.isFilledString(path)) {
		return false;
	}

	if (utils.existDir(path)) {
		return true;
	}

	utils.writeDir(path);
	log('wrote directory ' + path);
	return true;
}

function checkAndCreateFile(path, contentJson) {
	if (!utils.isFilledString(path)) {
		return false;
	}

	if (utils.existFile(path)) {
		return true;
	}

	utils.writeFile(path, JSON.stringify(contentJson));
	log('wrote file ' + path);
	return true;
}

function createDefinedDirectories(options) {

	checkAndCreateDirectory(options.restPath);
	checkAndCreateDirectory(options.restPath + '/_DTO');
	checkAndCreateDirectory(options.restPath + '/_collections');
	checkAndCreateFile(options.restPath + '/.validation.json', {});
	checkAndCreateFile(options.restPath + '/preferences.json', {});

	if (options.funcPath instanceof Array) {
		options.funcPath.forEach(function (funcPathItem) {
			checkAndCreateDirectory(funcPathItem);
		});
	} else {
		checkAndCreateDirectory(options.funcPath);
	}

	if (options.swaggerImport) {
		checkAndCreateDirectory(options.swaggerImport.responseFuncPath);
	}

}

module.exports = createDefinedDirectories;
