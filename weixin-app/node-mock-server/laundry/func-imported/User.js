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

	importedUser: function (loopArr) {

		var found1;

        if (!(loopArr instanceof Array)) {
        	loopArr = [];
        }

        found1 = loopArr.indexOf('importedUser');

        if (found1 >= 0) {
        	if (loopArr.indexOf('importedUser', found1 + 1) >= 0) {
        		return '{}';
        	}
        }

        loopArr.push('importedUser');

		return JSON.stringify({
  "id": faker.random.number(),
  "username": faker.name.findName(),
  "firstName": faker.name.firstName(),
  "lastName": faker.name.lastName(),
  "email": faker.internet.email(),
  "password": faker.lorem.word(),
  "phone": faker.phone.phoneNumber(),
  "userStatus": faker.random.number()
}, null, 2);
	}

};

/* jshint ignore:end */
/* eslint-enable */