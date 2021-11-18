
var ValidatorResponses = require('../lib/ValidatorResponses'),
	assert = require('assert');

module.exports = function(serverOptions, _getFile) {

	var pathAddresses = serverOptions.restPath + '/products/#{productCode}';

	new ValidatorResponses({
		restPath: serverOptions.restPath
	}, serverOptions);

	it('find attributes which are not defined in schema', function () {
		var data = JSON.parse(_getFile(pathAddresses + '/GET/.store.json')).validation['faker'];
		assert.equal(data.counter > 0, true);
	});

	it('find invalid value', function () {
		var data = JSON.parse(_getFile(pathAddresses + '/GET/.store.json')).validation['request-data'];
		assert.equal(data.counter === 1, true);
	});

	it('validation successful', function () {
		var data = JSON.parse(_getFile(pathAddresses + '/GET/.store.json')).validation['func'];
		assert.equal(data.counter === 0, true);
	});

	it('deep validation -  DTO references', function () {
		var data = JSON.parse(_getFile(pathAddresses + '/GET/.store.json')).validation['deep-validation'];
		assert.equal(data.counter === 0, true);
	});

};