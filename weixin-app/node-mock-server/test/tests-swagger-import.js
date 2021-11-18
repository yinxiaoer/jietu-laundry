
var assert = require('assert');

module.exports = function(serverOptions, _getFile) {

	var pathAddresses = serverOptions.swaggerImport.dest + '/addresses/#',
		pathExpSwagger = './test/expected/swagger-import';

	// GET /address
	it('response schema for GET /address should be written!', function () {
		var data = _getFile(pathAddresses + '/GET/response_schema.json'),
			expected = _getFile(pathExpSwagger + '/response_schema_01.json');
		assert.equal(data, expected);
	});

	it('description for GET /address should be written!', function () {
		var data = _getFile(pathAddresses + '/GET/desc.json'),
			expected = _getFile(pathExpSwagger + '/desc_01.json');
		assert.equal(data, expected);
	});

	// GET /address
	it('response schema for GET /address should be written!', function () {
		var data = _getFile(pathAddresses + '/GET/response_schema.json'),
			expected = _getFile(pathExpSwagger + '/response_schema_02.json');
		assert.equal(data, expected);
	});

	it('request schema for GET /address should be written!', function () {
		var data = _getFile(pathAddresses + '/GET/request_schema.json'),
			expected = _getFile(pathExpSwagger + '/request_schema_02.json');
		assert.equal(data, expected);
	});

	it('description for GET /address should be written!', function () {
		var data = _getFile(pathAddresses + '/GET/desc.json'),
			expected = _getFile(pathExpSwagger + '/desc_02.json');
		assert.equal(data, expected);
	});

};
