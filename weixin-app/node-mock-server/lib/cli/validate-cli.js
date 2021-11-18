/* eslint no-console: 0 */

var util = require('util');
var extend = util._extend;
var _defaults = require('../defaults/options-defaults');
var ValidatorResponses = require('../ValidatorResponses');

function validateCli(runServer, options) {

	options = extend(_defaults, options || {});
	options = extend(options, {
		onServerStarted: function (app, server) {
			var validatorResponses = new ValidatorResponses({
				restPath: options.restPath,
			}, options);

			server.close();
			return validatorResponses;
		},
	});

	runServer(options);
}

module.exports = validateCli;
