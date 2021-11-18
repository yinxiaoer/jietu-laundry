
var assert = require('assert'),
	request = require('request'),
	Utils = require('../lib/Utils'),
	DTOToClassConverter = require('../lib/DTOToClassConverter'),
	utils = new Utils();

function _fetch (opt) {
	request({
		uri: opt.url,
		method: opt.method || 'GET',
		form: opt.data || {}
	}, function(error, res, data) {
		if (error) {
			opt.error.call(this, data);
		} else {
			opt.success.call(this, data);
		}
	});
}

function _url (baseUrl, options) {

	var query = [];

	utils.forIn(options, function (key, value) {
		query.push(key + '=' + encodeURIComponent(value));
	});

	return baseUrl + '/service/class-dto?' + query.join('&');
}

module.exports = function(serverOptions, _getFile) {

	var pathExpected = './test/expected/dto-2-class',
		pathDTO = './test/tmp/swagger-import/_DTO/',
		baseUrl = serverOptions.urlBase;

	it('test normalize method', function () {
		var data = require('./data/dto-2-class/normalize_01'),
			result = DTOToClassConverter.prototype.normalize(data),
			expected = require('./expected/dto-2-class/normalize_01');

		assert.deepEqual(result.refs, expected.refs);
		assert.deepEqual(result.attributes, expected.attributes);
	});

	it('test normalize method 3', function () {
		var data = require('./data/dto-2-class/normalize_03'),
			result = DTOToClassConverter.prototype.normalize(data),
			expected = require('./expected/dto-2-class/normalize_03');

		assert.deepEqual(result.refs, expected.refs);
		assert.deepEqual(result.attributes, expected.attributes);
	});

	it('ES6 ComplexDTO Class', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6',
				setter: 'true',
				getter: 'true',
				validator: 'true',
				path: pathDTO + 'ComplexDTO.json',
				name: 'ComplexDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/ComplexDTO-es6.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 Flow ComplexDTO Class', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6flow',
				setter: 'true',
				getter: 'true',
				validator: 'true',
				path: pathDTO + 'ComplexDTO.json',
				name: 'ComplexDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/ComplexDTO-es6flow.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 AddressWsDTO Class without validators', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6flow',
				setter: 'true',
				getter: 'true',
				validator: 'false',
				path: pathDTO + 'AddressWsDTO.json',
				name: 'AddressWsDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/AddressWsDTO-es6flow-1.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 AddressWsDTO Class without setter', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6flow',
				setter: 'false',
				getter: 'true',
				validator: 'true',
				path: pathDTO + 'AddressWsDTO.json',
				name: 'AddressWsDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/AddressWsDTO-es6flow-2.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 AddressWsDTO Class without getter', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6flow',
				setter: 'true',
				getter: 'false',
				validator: 'true',
				path: pathDTO + 'AddressWsDTO.json',
				name: 'AddressWsDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/AddressWsDTO-es6flow-3.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 AddressWsDTO Class just getter', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6flow',
				setter: 'false',
				getter: 'true',
				validator: 'false',
				path: pathDTO + 'AddressWsDTO.json',
				name: 'AddressWsDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/AddressWsDTO-es6flow-4.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

	it('ES6 AddressWsDTO Class', function (done) {
		_fetch({
			url: _url(baseUrl, {
				es: 'es6',
				setter: 'true',
				getter: 'true',
				validator: 'true',
				path: pathDTO + 'AddressWsDTO.json',
				name: 'AddressWsDTO'
			}),
			success: function (data) {
				var expected = _getFile(pathExpected + '/AddressWsDTO-es6-1.js');
				assert.equal(data, expected);
				done();
			}
		});
	});

};
