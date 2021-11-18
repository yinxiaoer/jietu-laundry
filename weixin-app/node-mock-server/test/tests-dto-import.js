
var assert = require('assert');

module.exports = function(serverOptions, _getFile) {

	var pathDTO = serverOptions.swaggerImport.dest + '/_DTO',
		pathExpSwagger = './test/expected/dto-import';

	// AddressResponseWsDTO
	it('DTO Schema for AddressResponseWsDTO should be written!', function () {
		var data = _getFile(pathDTO + '/AddressResponseWsDTO.json'),
			expected = _getFile(pathExpSwagger + '/AddressResponseWsDTO.json');
		assert.equal(data, expected);
	});

	// AddressWsDTO
	it('DTO Schema for AddressWsDTO should be written!', function () {
		var data = _getFile(pathDTO + '/AddressWsDTO.json'),
			expected = _getFile(pathExpSwagger + '/AddressWsDTO.json');
		assert.equal(data, expected);
	});

	// CountryWsDTO
	it('DTO Schema for CountryWsDTO should be written!', function () {
		var data = _getFile(pathDTO + '/CountryWsDTO.json'),
			expected = _getFile(pathExpSwagger + '/CountryWsDTO.json');
		assert.equal(data, expected);
	});

	// WithArrayDTO
	it('DTO Schema for ComplexDTO should be written!', function () {
		var data = _getFile(pathDTO + '/ComplexDTO.json'),
			expected = _getFile(pathExpSwagger + '/ComplexDTO.json');
		assert.equal(data, expected);
	});

};
