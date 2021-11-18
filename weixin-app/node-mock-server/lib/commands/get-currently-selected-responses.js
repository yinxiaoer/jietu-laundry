'use strict';

var Utils = require('../Utils');
var utils = new Utils();
var ignoreInRestRoot = require('../constants/ignore-in-rest-root');

function getCurrentlySelectedResponses(options) {

	var path = `${options.restPath}`;
	var serviceGroups = utils.readDir(path, ignoreInRestRoot);
	var selectedResponses = {};

	serviceGroups.forEach(function (serviceGroup) {
		var endPoints = utils.readDir(serviceGroup.path);
		endPoints.forEach(function (endPoint) {
			var services = utils.readDir(endPoint.path);
			services.forEach(function (service) {
				var name = `${serviceGroup.file}/${endPoint.file}/${service.file}`;
				var pathSelectedFile = `${service.path}/mock/response.txt`;

				if (!utils.existFile(pathSelectedFile)) {
					return;
				}

				var fileData = utils.readFile(pathSelectedFile);

				if (fileData === 'success') {
					return;
				}

				selectedResponses[name] = fileData;
			});
		});
	});

	return selectedResponses;
}

module.exports = getCurrentlySelectedResponses;
