'use strict';

var Utils = require('./Utils');
var utils = new Utils();

function getCertificate(pathOrFile) {
	if (pathOrFile.split('\n').length > 1) {
		return pathOrFile;
	}
	return utils.readFile(pathOrFile);
}

module.exports = getCertificate;
