/* eslint-disable */
/* jshint ignore:start */
'use strict';

var faker = require('faker');

function _getArray(getData) {
	var out = [],
		len = faker.random.number(10) + 1,
		i;

	for (i = 0; i < len; i += 1) {
		out.push(getData());
	}

	return out;
}


function _getRes(name, loopArr) {
	try {
		return JSON.parse(require(__dirname + '/' + name + '.js')['imported' + name](loopArr.slice()));
	} catch (err) {}

	return {};
}

module.exports = {

	importedPet: function (loopArr) {

		var found1;

        if (!(loopArr instanceof Array)) {
        	loopArr = [];
        }

        found1 = loopArr.indexOf('importedPet');

        if (found1 >= 0) {
        	if (loopArr.indexOf('importedPet', found1 + 1) >= 0) {
        		return '{}';
        	}
        }

        loopArr.push('importedPet');

		return JSON.stringify({
  "id": faker.random.number(),
  "category": _getRes('Category', loopArr),
  "name": faker.name.findName(),
  "photoUrls": _getArray(function () {return {};}),
  "tags": _getArray(function () {return _getRes('Tag', loopArr);}),
  "status": faker.lorem.word()
}, null, 2);
	}

};

/* jshint ignore:end */
/* eslint-enable */