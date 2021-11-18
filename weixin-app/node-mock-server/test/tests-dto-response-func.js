
var assert = require('assert');

module.exports = function(serverOptions, _getFile) {

	var pathFunc = serverOptions.swaggerImport.responseFuncPath,
		pathExpSwagger = './test/expected/dto-response-func';

	// ResponseFuncTestDTO
	it('DTO Response Func (ResponseFuncTestDTO) should be written!', function () {
		var data = _getFile(pathFunc + '/ResponseFuncTestDTO.js'),
			expected = _getFile(pathExpSwagger + '/ResponseFuncTestDTO.js');
		assert.equal(data, expected);
	});

	// RuleWsDTO
	it('DTO Rule (RuleWsDTO) should be written!', function () {
		var data = _getFile(pathFunc + '/RuleWsDTO.js'),
			expected = _getFile(pathExpSwagger + '/RuleWsDTO.js');
		assert.equal(data, expected);
	});

};
