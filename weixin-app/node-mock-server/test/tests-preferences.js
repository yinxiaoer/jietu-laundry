
var assert = require('assert'),
	request = require('request');

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

module.exports = function(serverOptions, _getFile) {

	var baseUrl = serverOptions.urlBase;

	it('Change responseDelay to 333', function (done) {
		_fetch({
			url: baseUrl + '/service/preferences',
			method: 'OPTIONS',
			data: {
				key: 'responseDelay',
				value: 333
			},
			success: function () {
				var storedPreferences = JSON.parse(_getFile(serverOptions.restPath + '/preferences.json'));
				assert.equal(storedPreferences.responseDelay, 333);
				done();
			}
		});
	});

	it('Change responseDelay to 0', function (done) {
		_fetch({
			url: baseUrl + '/service/preferences',
			method: 'OPTIONS',
			data: {
				key: 'responseDelay',
				value: 0
			},
			success: function () {
				var storedPreferences = JSON.parse(_getFile(serverOptions.restPath + '/preferences.json'));
				assert.equal(storedPreferences.responseDelay, 0);
				done();
			}
		});
	});

};
